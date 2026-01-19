package com.softka.account_service.controller;


import com.softka.account_service.model.dto.BankStatementDto;
import com.softka.account_service.model.dto.TransactionDto;
import com.softka.account_service.model.dto.validation.CreateGroup;
import com.softka.account_service.service.ITransactionService;
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

    private final ITransactionService ITransactionService;

    public TransactionController(ITransactionService ITransactionService) {
        this.ITransactionService = ITransactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll(){
        return ResponseEntity.ok(ITransactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id){
        return ResponseEntity.ok(ITransactionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody @Validated(CreateGroup.class)
                                                     TransactionDto transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ITransactionService.create(transactionDto));
    }

    @GetMapping("/{clientId}/reportes")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return ResponseEntity
                .ok(ITransactionService.getAllBankStatementByClientIdAndDateBetween(clientId,
                        from,to));
    }

}
