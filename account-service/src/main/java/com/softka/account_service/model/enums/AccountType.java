package com.softka.account_service.model.enums;

public enum AccountType {

    Corriente("Cuenta Corriente"),
    Ahorro("Cuenta de Ahorro");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
