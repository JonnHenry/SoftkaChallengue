package com.softka.account_service.model.dto;


import com.softka.account_service.model.Account;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionDto {

    @EqualsAndHashCode.Include
    private Long transactionId;

    @NotNull(message = "El número de cuenta no puede ser nulo")
    private Long accountId;

    private LocalDateTime transactionDate;

    @NotNull(message = "El tipo de transacción no puede ser nulo")
    private String transactionType;

    @NotNull(message = "El valor no puede ser nulo")
    private double amount;

    private double balance;

    private Account Account;

}
