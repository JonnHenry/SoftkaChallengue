package com.softka.dto;

import com.softka.enums.AccountType;
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
