package com.softka.account_service.model.dto;

import com.softka.account_service.model.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventAccountRequest {

    private String accountNumber;
    private AccountType typeAccount;
    private double initialBalance;
    private Long clientId;
}
