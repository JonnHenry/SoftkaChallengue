package com.softka.account_service.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softka.account_service.model.Account;
import com.softka.account_service.model.dto.validation.CreateGroup;
import com.softka.account_service.model.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionDto {

    @EqualsAndHashCode.Include
    private Long transactionId;

    @NotNull(message = "El número de cuenta no puede ser nulo",groups = {CreateGroup.class})
    private Long accountId;

    private LocalDateTime transactionDate;

    @NotNull(message = "El tipo de transacción no puede ser nulo",groups = {CreateGroup.class})
    private TransactionType transactionType;

    private double amount;

    private double balance;

}
