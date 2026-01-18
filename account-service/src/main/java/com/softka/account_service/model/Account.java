package com.softka.account_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softka.account_service.model.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TACCOUNTS")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private Long accountId;

    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false, updatable = false)
    @Pattern(regexp = "\\d+", message = "El número de cuenta debe de ser numerico")
    @NotEmpty(message = "El número de cuenta es requerido")
    private String number;

    @Column(name = "ACCOUNT_TYPE", nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de cuenta no puede ser nulo")
    private AccountType accountType;

    @Column(name = "INITIAL_AMOUNT", nullable = false)
    @Min(0)
    private double initialAmount;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "CLIENT_ID", nullable = false, updatable = false)
    private Long clientId;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Transaction> transactions;


    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive=isActive;
    }
}
