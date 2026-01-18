package com.softka.customer_service.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.softka.customer_service.model.enums.AccountType;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountRequestDto {

    @EqualsAndHashCode.Include
    private Long accountId;

    private String number;

    private AccountType accountType;

    private double initialAmount;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonProperty(value = "isActive")
    private boolean isActive;

    private Long clientId;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
