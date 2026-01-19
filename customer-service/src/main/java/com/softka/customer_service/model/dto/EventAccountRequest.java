package com.softka.customer_service.model.dto;

import com.softka.customer_service.model.enums.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventAccountRequest {

    private String accountNumber;
    private AccountType typeAccount;
    private double initialBalance;
    private Long clientId;
}
