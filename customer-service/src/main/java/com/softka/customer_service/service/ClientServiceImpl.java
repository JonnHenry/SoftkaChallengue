package com.softka.customer_service.service;

import com.softka.customer_service.constants.ClientConstants;
import com.softka.customer_service.events.ClientEvent;
import com.softka.customer_service.exception.AlreadyExistException;
import com.softka.customer_service.exception.NotFoundException;
import com.softka.customer_service.mapper.ClientMapper;
import com.softka.customer_service.model.Client;
import com.softka.customer_service.model.dto.ClientAccountDto;
import com.softka.customer_service.model.dto.ClientDto;
import com.softka.customer_service.model.dto.EventAccountRequest;
import com.softka.customer_service.model.enums.EventType;
import com.softka.customer_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.core.env.Environment;

@Service
public class ClientServiceImpl implements IClientService {

    @Value("${kafka.client-topic}")
    private String clientTopic;

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;
    private final Environment env;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder,
                             KafkaProducerService kafkaProducerService, Environment env) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducerService = kafkaProducerService;
        this.env = env;
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
    @Transactional
    public ClientAccountDto create(ClientAccountDto clientAccountDto) {
        Client clientFound = findByDniEntity(clientAccountDto.getDni());
        ClientDto clientFoundDto;
        if (clientFound==null) {
            clientFound = Client.builder()
                    .dni(clientAccountDto.getDni())
                    .name(clientAccountDto.getName())
                    .password(clientAccountDto.getPassword())
                    .gender(clientAccountDto.getGender())
                    .age(clientAccountDto.getAge())
                    .address(clientAccountDto.getAddress())
                    .phone(clientAccountDto.getPhone())
                    .build();

            clientFoundDto = create(ClientMapper.INSTANCE.toDTO(clientFound));
        }else {
            clientFoundDto = update(ClientMapper.INSTANCE.toDTO(clientFound));
        }
        EventAccountRequest accountRequest = EventAccountRequest.builder()
                .accountNumber(clientAccountDto.getNumberAccount())
                .initialBalance(clientAccountDto.getInitialAmount())
                .typeAccount(clientAccountDto.getAccountType())
                .clientId(clientFoundDto.getId())
                .build();

        ClientEvent event = new ClientEvent();
        event.setId(UUID.randomUUID().toString());
        event.setDate(new Date());
        event.setData(accountRequest);
        event.setType(EventType.CREATED);
        kafkaProducerService.sendPublish(clientTopic, event);

        clientAccountDto.setClientId(clientFoundDto.getId());
        return clientAccountDto;
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
