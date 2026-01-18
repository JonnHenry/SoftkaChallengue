package com.softka.account_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softka.account_service.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="TRANSACTIONS")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private Long transactionId;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private TransactionType transactionType;

    @Column(name = "AMOUNT", nullable = false)
    private double amount;

    @Column(name = "BALANCE", nullable = false)
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", insertable=false,updatable = false)
    @JsonIgnore
    private Account account;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

}
