package com.softka.account_service.repository;

import com.softka.account_service.model.Transaction;
import com.softka.account_service.model.dto.BankStatementDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {


    @Query(value = "SELECT t FROM Transaction t where t.accountId=:accountId ORDER BY t.transactionDate DESC LIMIT 1")
    Transaction getLastByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT new com.softka.account_service.model.dto.BankStatementDto("+
            "t.transactionDate,"+
            "str(a.clientId),"+
            "a.number,"+
            "a.accountType,"+
            "a.initialAmount,"+
            "a.isActive,"+
            "t.transactionType,"+
            "t.amount,"+
            "t.balance)"+
            "FROM Transaction t JOIN t.account a "+
            "WHERE a.clientId=:clientId and t.transactionDate BETWEEN :dateTransactionStart AND :dateTransactionEnd")
    List<BankStatementDto> getAllByAccountClientIdAndDateBetween(@Param("clientId") Long clientId,
                                                                        @Param("dateTransactionStart") LocalDateTime dateTransactionStart,
                                                                        @Param("dateTransactionEnd") LocalDateTime  dateTransactionEnd);
}
