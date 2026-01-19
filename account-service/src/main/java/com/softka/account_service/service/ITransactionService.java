package com.softka.account_service.service;

import com.softka.account_service.model.dto.BankStatementDto;
import com.softka.account_service.model.dto.TransactionDto;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface ITransactionService {
    /**
     Get all transactions
     @return List<TransactionDto>
     */
    List<TransactionDto> getAll();

    /**
     Get a transaction by id
     @return TransactionDto
     */
    TransactionDto getById(Long id);

    /**
     Create a transaction
     @param transactionDto
     @return TransactionDto
     */
    TransactionDto create(TransactionDto transactionDto);

    /**
     Get all bank statements by user
     @param clientId
     @param dateTransactionEnd
     @param dateTransactionStart
     @return List<BankStatementDto>
     */
    List<BankStatementDto> getAllBankStatementByClientIdAndDateBetween(Long clientId,
                                                                        @Param("dateTransactionStart") Date dateTransactionStart,
                                                                        @Param("dateTransactionEnd") Date  dateTransactionEnd);

    /**
     Get last transaction by account id
     @param accountId
     @return List<BankStatementDto>
     */
    TransactionDto getLastTransactionByAccountId(Long accountId);
}
