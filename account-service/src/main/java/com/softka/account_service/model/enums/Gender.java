package com.softka.account_service.model.enums;

public enum Gender {

    F("Femenino"),
    M("Masculino"),;

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
