package com.softka.account_service.controller;

import com.softka.account_service.model.dto.AccountDto;
import com.softka.account_service.model.dto.validation.CreateGroup;
import com.softka.account_service.model.dto.validation.UpdateGroup;
import com.softka.account_service.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController  {

    private final IAccountService IAccountService;

    public AccountController(IAccountService IAccountService) {
        this.IAccountService = IAccountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(IAccountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(IAccountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody @Validated(CreateGroup.class) AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(IAccountService.create(accountDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable Long id,
                                             @RequestBody @Validated(UpdateGroup.class) AccountDto accountDto) {
        accountDto.setAccountId(id);
        return ResponseEntity.ok(IAccountService.update(accountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        IAccountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
