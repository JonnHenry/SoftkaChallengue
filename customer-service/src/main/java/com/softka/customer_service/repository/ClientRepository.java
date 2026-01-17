package com.softka.customer_service.repository;

import com.softka.customer_service.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     Get a client by dbi
     @param dni
     @return Client
     */
    Optional<Client>  findByDni(String dni);
}
