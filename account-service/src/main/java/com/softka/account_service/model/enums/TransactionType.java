package com.softka.account_service.model.enums;

public enum TransactionType {

    Retiro("Retiro"),
    Deposito("Depósito");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }
}
