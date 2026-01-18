package com.softka.account_service.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softka.account_service.model.Transaction;
import com.softka.account_service.model.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountDto {

    @EqualsAndHashCode.Include
    private Long accountId;

    @NotEmpty
    @Pattern(regexp = "\\d+", message = "El número de cuenta debe de ser numerico")
    @NotNull(message = "El número de cuenta no debe de ser nulo")
    private String number;

    @NotNull
    private AccountType accountType;

    @Min(0)
    private double initialAmount;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonProperty(value = "isActive")
    private boolean isActive;

    @NotNull(message = "El id del cliente no puede ser nulo")
    private Long clientId;

    @JsonIgnore
    List<Transaction> transactions;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
