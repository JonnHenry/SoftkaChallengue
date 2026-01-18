package com.softka.account_service.controller;


import com.softka.account_service.model.dto.BankStatementDto;
import com.softka.account_service.model.dto.TransactionDto;
import com.softka.account_service.model.dto.validation.CreateGroup;
import com.softka.account_service.service.TransactionService;
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

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll(){
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody @Validated(CreateGroup.class)
                                                     TransactionDto transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(transactionDto));
    }

    @GetMapping("/{clientId}/reportes")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return ResponseEntity
                .ok(transactionService.getAllBankStatementByClientIdAndDateBetween(clientId,
                        from,to));
    }

}
