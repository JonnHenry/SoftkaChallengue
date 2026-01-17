package com.softka.customer_service.controller;

import com.softka.customer_service.model.dto.ClientDto;
import com.softka.customer_service.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/clientes")
@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll(){
        List<ClientDto> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> get(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto){
        clientDto.setId(id);
        return ResponseEntity.ok(clientService.update(clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
