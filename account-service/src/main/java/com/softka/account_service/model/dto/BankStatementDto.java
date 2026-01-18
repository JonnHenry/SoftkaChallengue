package com.softka.account_service.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.softka.account_service.model.enums.AccountType;
import com.softka.account_service.model.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankStatementDto {

    private LocalDateTime transactionDate;
    private String clientId;
    private String number;
    private AccountType accountType;
    private double initialAmount;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonProperty(value = "isActive")
    private boolean isActive;

    private TransactionType transactionType;
    private double amount;
    private double balance;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
