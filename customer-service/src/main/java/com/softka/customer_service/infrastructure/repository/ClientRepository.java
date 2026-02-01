package com.softka.customer_service.infrastructure.repository;

import com.softka.customer_service.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     Get a client by dbi
     @param dni
     @return Client
     */
    Optional<Client>  findByDni(String dni);
}
