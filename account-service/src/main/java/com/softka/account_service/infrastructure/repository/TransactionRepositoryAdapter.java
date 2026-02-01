package com.softka.account_service.infrastructure.repository;

import com.softka.account_service.application.port.out.TransactinoRepositoryPort;
import com.softka.account_service.domain.model.Transaction;
import com.softka.account_service.infrastructure.dto.BankStatementDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryAdapter implements TransactinoRepositoryPort {

    private final TransactionRepository transactionRepository;

    public TransactionRepositoryAdapter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }


    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<BankStatementDto> getAllBankStatementByClientIdAndDateBetween(Long clientId,
                                                                              LocalDateTime dateTransactionStart,
                                                                              LocalDateTime dateTransactionEnd) {
        return transactionRepository.getAllByAccountClientIdAndDateBetween(clientId,dateTransactionStart,
                dateTransactionEnd);
    }

    @Override
    public Transaction getLastTransactionByAccountId(Long accountId) {
        return null;
    }
}
