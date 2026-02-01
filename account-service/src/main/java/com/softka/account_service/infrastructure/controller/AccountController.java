package com.softka.account_service.infrastructure.controller;

import com.softka.account_service.application.port.in.AccountService;
import com.softka.account_service.infrastructure.dto.AccountDto;
import com.softka.validator.CreateGroup;
import com.softka.validator.UpdateGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController  {

    private final AccountService AccountService;

    public AccountController(AccountService AccountService) {
        this.AccountService = AccountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(AccountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(AccountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody @Validated(CreateGroup.class) AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountService.create(accountDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable Long id,
                                             @RequestBody @Validated(UpdateGroup.class) AccountDto accountDto) {
        accountDto.setAccountId(id);
        return ResponseEntity.ok(AccountService.update(accountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        AccountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
