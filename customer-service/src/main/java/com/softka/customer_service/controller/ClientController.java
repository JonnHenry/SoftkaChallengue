package com.softka.customer_service.controller;

import com.softka.customer_service.model.dto.ClientAccountDto;
import com.softka.customer_service.model.dto.ClientDto;
import com.softka.customer_service.model.dto.validation.CreateGroup;
import com.softka.customer_service.model.dto.validation.UpdateGroup;
import com.softka.customer_service.service.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/clientes")
@RestController
public class ClientController {

    private final IClientService IClientService;

    public ClientController(IClientService IClientService) {
        this.IClientService = IClientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll(){
        List<ClientDto> clients = IClientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> get(@PathVariable Long id){
        return ResponseEntity.ok(IClientService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Validated(CreateGroup.class) ClientDto clientDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(IClientService.create(clientDto));
    }

    @PostMapping("/cuentas")
    public ResponseEntity<ClientAccountDto> createClientAccount(@RequestBody @Validated(CreateGroup.class) ClientAccountDto clientAccountDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(IClientService.create(clientAccountDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody @Validated(UpdateGroup.class) ClientDto clientDto){
        clientDto.setId(id);
        return ResponseEntity.ok(IClientService.update(clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        IClientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
