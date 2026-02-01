package com.softka.customer_service.application.port.out;

import com.softka.customer_service.domain.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {

    /**
     Get all clients
     @return List<Client>
     */
     List<Client> findAll();

    /**
     Get a client by id
     @param id
     @return Optional<Client>
     */
    Optional<Client> findById(Long id);

    /**
     Get a client by id
     @param dni
     @return Optional<Client>
     */
    Optional<Client> findByDniEntity(String dni);

    /**
     Create a client
     @param client
     @return Client
     */
    Client save(Client client);

    /**
     Update a client
     @param clientDto
     @return ClientDto
     */
    Client update(Client clientDto);

    /**
     Delete a client by id
     @param id
     */
    void deleteById(Long id);
}
