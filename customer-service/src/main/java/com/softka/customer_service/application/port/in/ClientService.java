package com.softka.customer_service.application.port.in;

import com.softka.customer_service.infrastructure.dto.ClientAccountDto;
import com.softka.customer_service.infrastructure.dto.ClientDto;
import com.softka.customer_service.domain.model.Client;

import java.util.List;

public interface ClientService {

    /**
        Get all clients
        @return List<ClientDto>
     */
    public List<ClientDto> getAll();

    /**
     Get a client by id
     @param id
     @return ClientDto
     */
    public ClientDto getById(Long id);

    /**
     Get a client by id
     @param dni
     @return ClientDto
     */
    ClientDto findByDniDto(String dni);


    /**
     Get a client by id
     @param dni
     @return Client
     */
    Client findByDniEntity(String dni);

    /**
     Create a client
     @param clientDto
     @return ClientDto
     */
    ClientDto create(ClientDto clientDto);

    /**
     Create a client account
     @param clientAccountDto
     @return ClientDto
     */
    ClientAccountDto create(ClientAccountDto clientAccountDto);

    /**
     Update a client
     @param clientDto
     @return ClientDto
     */
    public ClientDto update(ClientDto clientDto);

    /**
     Delete a client by id
     @param id
     */
    public void deleteById(Long id);
}
