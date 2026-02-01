package com.softka.account_service.infrastructure.controller;

import com.softka.account_service.application.port.in.TransactionService;
import com.softka.account_service.infrastructure.dto.BankStatementDto;
import com.softka.account_service.infrastructure.dto.TransactionDto;
import com.softka.validator.CreateGroup;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class TransactionController {

    private final TransactionService TransactionService;

    public TransactionController(TransactionService TransactionService) {
        this.TransactionService = TransactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll(){
        return ResponseEntity.ok(TransactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id){
        return ResponseEntity.ok(TransactionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody @Validated(CreateGroup.class)
                                                     TransactionDto transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionService.create(transactionDto));
    }

    @GetMapping("/{clientId}/reportes")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return ResponseEntity
                .ok(TransactionService.getAllBankStatementByClientIdAndDateBetween(clientId,
                        from,
                        to));
    }

}
