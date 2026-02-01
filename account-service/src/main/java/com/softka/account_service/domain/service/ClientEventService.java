package com.softka.account_service.domain.service;


import com.softka.account_service.application.port.in.AccountService;
import com.softka.account_service.infrastructure.dto.AccountDto;
import com.softka.dto.EventAccountRequest;
import com.softka.enums.EventType;
import com.softka.events.ClientEvent;
import com.softka.events.Event;
import com.softka.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientEventService {

    private final AccountService AccountService;

    @Value("${kafka.client-topic}")
    private String clientTopic;

    public ClientEventService(AccountService accountService) {
        AccountService = accountService;
    }

    @KafkaListener(
            topics = "CLIENTS",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "grupo1"
    )
    public void consumer(Event<?> event) {
        try {
            if (event.getClass().isAssignableFrom(ClientEvent.class)) {
                ClientEvent clientEvent = (ClientEvent) event;
                if (EventType.CREATED.equals(clientEvent.getType())) {
                    EventAccountRequest eventAccountRequest = clientEvent.getData();

                    AccountDto accountDto = new AccountDto();
                    accountDto.setClientId(eventAccountRequest.getClientId());
                    accountDto.setAccountType(eventAccountRequest.getTypeAccount());
                    accountDto.setInitialAmount(eventAccountRequest.getInitialBalance());
                    accountDto.setNumber(eventAccountRequest.getAccountNumber());
                    AccountService.create(accountDto);
                }
            }
        } catch (NotFoundException e) {
            // Loguear y continuar sin lanzar excepción
            log.warn("Cuenta no encontrada para el cliente: {}", event.getData());
        } catch (Exception e) {
            // Otros errores graves
            log.error("Error procesando evento: {}", event, e);
        }
    }
}
