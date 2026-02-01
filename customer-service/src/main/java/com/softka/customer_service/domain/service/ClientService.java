package com.softka.customer_service.domain.service;

import com.softka.customer_service.infrastructure.constants.ClientConstants;
import com.softka.customer_service.infrastructure.mapper.ClientMapper;
import com.softka.customer_service.domain.model.Client;
import com.softka.customer_service.infrastructure.dto.ClientAccountDto;
import com.softka.customer_service.infrastructure.dto.ClientDto;
import com.softka.customer_service.infrastructure.repository.ClientRepositoryAdapter;
import com.softka.dto.EventAccountRequest;
import com.softka.enums.EventType;
import com.softka.events.ClientEvent;
import com.softka.exception.AlreadyExistException;
import com.softka.exception.NotFoundException;
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
public class ClientService implements com.softka.customer_service.application.port.in.ClientService {

    @Value("${kafka.client-topic}")
    private String clientTopic;

    private final ClientRepositoryAdapter clientRepositoryAdapter;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;
    private final Environment env;

    public ClientService(ClientRepositoryAdapter clientRepositoryAdapter, PasswordEncoder passwordEncoder,
                         KafkaProducerService kafkaProducerService, Environment env) {
        this.clientRepositoryAdapter = clientRepositoryAdapter;
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
        return clientRepositoryAdapter.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto getById(Long id) {
        return clientRepositoryAdapter.findById(id).map(ClientMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto findByDniDto(String dni) {
        return clientRepositoryAdapter.findByDniEntity(dni).map(ClientMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client findByDniEntity(String dni) {
        return clientRepositoryAdapter.findByDniEntity(dni).orElse(null);
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
                clientRepositoryAdapter.save(ClientMapper.INSTANCE.toEntity(clientDto)));
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
        Client client = clientRepositoryAdapter.findById(id).orElseThrow(
                ()->new NotFoundException(String.format(ClientConstants.CLIENT_NOT_EXIST, id))
        );
        if (Objects.nonNull(clientDto.getPassword())) {
            client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        }
        ClientMapper.INSTANCE.updateEntityFromDTO(clientDto, client);
        return ClientMapper.INSTANCE.toDTO(clientRepositoryAdapter.save(client));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        Client client = clientRepositoryAdapter.findById(id).orElseThrow(
                ()->new NotFoundException(String.format(ClientConstants.CLIENT_NOT_EXIST, id))
        );
        clientRepositoryAdapter.deleteById(id);
    }
}
