package com.softka.customer_service.infrastructure.controller;

import com.softka.customer_service.infrastructure.dto.ClientAccountDto;
import com.softka.customer_service.infrastructure.dto.ClientDto;
import com.softka.customer_service.application.port.in.ClientService;
import com.softka.validator.CreateGroup;
import com.softka.validator.UpdateGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/clientes")
@RestController
public class ClientController {

    private final ClientService ClientService;

    public ClientController(ClientService ClientService) {
        this.ClientService = ClientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll(){
        List<ClientDto> clients = ClientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> get(@PathVariable Long id){
        return ResponseEntity.ok(ClientService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Validated(CreateGroup.class) ClientDto clientDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientService.create(clientDto));
    }

    @PostMapping("/cuentas")
    public ResponseEntity<ClientAccountDto> createClientAccount(@RequestBody @Validated(CreateGroup.class) ClientAccountDto clientAccountDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientService.create(clientAccountDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody @Validated(UpdateGroup.class) ClientDto clientDto){
        clientDto.setId(id);
        return ResponseEntity.ok(ClientService.update(clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ClientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
