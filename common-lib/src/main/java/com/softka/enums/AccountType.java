package com.softka.enums;

public enum AccountType {

    Corriente("Cuenta Corriente"),
    Ahorro("Cuenta de Ahorro");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
