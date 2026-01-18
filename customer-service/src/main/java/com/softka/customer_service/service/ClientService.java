package com.softka.customer_service.service;

import com.softka.customer_service.model.Client;
import com.softka.customer_service.model.dto.ClientAccountDto;
import com.softka.customer_service.model.dto.ClientDto;

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
