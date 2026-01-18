package com.softka.customer_service.service;

import com.softka.customer_service.constants.ClientConstants;
import com.softka.customer_service.exception.AlreadyExistException;
import com.softka.customer_service.exception.NotFoundException;
import com.softka.customer_service.mapper.AccountMapper;
import com.softka.customer_service.mapper.ClientMapper;
import com.softka.customer_service.model.Client;
import com.softka.customer_service.model.dto.AccountRequestDto;
import com.softka.customer_service.model.dto.ClientAccountDto;
import com.softka.customer_service.model.dto.ClientDto;
import com.softka.customer_service.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository,PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto getById(Long id) {
        return clientRepository.findById(id).map(ClientMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto findByDniDto(String dni) {
        return clientRepository.findByDni(dni).map(ClientMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client findByDniEntity(String dni) {
        return clientRepository.findByDni(dni).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public ClientDto create(ClientDto clientDto) {
        ClientDto clientFound = findByDniDto(clientDto.getDni());
        if (Objects.nonNull(clientFound)) {
            throw new AlreadyExistException(String.format(ClientConstants.CLIENT_ALREADY_EXIST,clientDto.getDni()));
        }
        clientDto.setIsActive(true);
        clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        return ClientMapper.INSTANCE.toDTO(
                clientRepository.save(ClientMapper.INSTANCE.toEntity(clientDto)));
    }

    @Override
    public ClientAccountDto create(ClientAccountDto clientAccountDto) {
        //Valido primero de que exista el cliente si el cliente existe pues no es necesario crearlo solo recupero datos
        //en caso de que no exista lo creo
        Client clientFound = findByDniEntity(clientAccountDto.getDni());
        ClientDto clientFoundDto;
        if (clientFound==null) {
            clientFoundDto = create(ClientMapper.INSTANCE.toDTO(clientFound));
        }else {
            clientFoundDto = update(ClientMapper.INSTANCE.toDTO(clientFound));
        }
        AccountRequestDto accountDto = AccountMapper.INSTANCE.toAccountRequestDto(clientAccountDto);
        accountDto.setClientId(clientFoundDto.getId());

        //TODO: Call a kafka topic to create account

        clientAccountDto.setClientId(clientFoundDto.getId());
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public ClientDto update(ClientDto clientDto) {
        Long id = clientDto.getId();
        Client client = clientRepository.findById(id).orElseThrow(
                ()->new NotFoundException(String.format(ClientConstants.CLIENT_NOT_EXIST, id))
        );
        if (Objects.nonNull(clientDto.getPassword())) {
            client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        }
        ClientMapper.INSTANCE.updateEntityFromDTO(clientDto, client);
        return ClientMapper.INSTANCE.toDTO(clientRepository.save(client));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                ()->new NotFoundException(String.format(ClientConstants.CLIENT_NOT_EXIST, id))
        );
        clientRepository.deleteById(id);
    }
}
