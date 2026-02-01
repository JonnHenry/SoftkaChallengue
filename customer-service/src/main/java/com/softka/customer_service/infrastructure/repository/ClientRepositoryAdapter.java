package com.softka.customer_service.infrastructure.repository;

import com.softka.customer_service.application.port.out.ClientRepositoryPort;
import com.softka.customer_service.domain.model.Client;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository  clientRepository;

    public ClientRepositoryAdapter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> findByDniEntity(String dni) {
        return clientRepository.findByDni(dni);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client clientDto) {
        return clientRepository.save(clientDto);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
