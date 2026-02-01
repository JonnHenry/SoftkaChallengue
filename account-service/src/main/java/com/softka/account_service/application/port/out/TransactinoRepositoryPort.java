package com.softka.account_service.application.port.out;

import com.softka.account_service.domain.model.Transaction;
import com.softka.account_service.infrastructure.dto.BankStatementDto;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactinoRepositoryPort {

    /**
     Get all transactions
     @return List<Transaction>
     */
    List<Transaction> findAll();


    /**
     Get a transaction by id
     @return Optional<Transaction>
     */
    Optional<Transaction> findById(Long id);

    /**
     Create a transaction
     @param transaction
     @return Transaction
     */
    Transaction create(Transaction transaction);

    /**
     Get all bank statements by user
     @param clientId
     @param dateTransactionEnd
     @param dateTransactionStart
     @return List<BankStatementDto>
     */
    List<BankStatementDto> getAllBankStatementByClientIdAndDateBetween(Long clientId,
                                                                       @Param("dateTransactionStart") LocalDateTime dateTransactionStart,
                                                                       @Param("dateTransactionEnd") LocalDateTime  dateTransactionEnd);

    /**
     Get last transaction by account id
     @param accountId
     @return Transaction
     */
    Transaction getLastTransactionByAccountId(Long accountId);
}
