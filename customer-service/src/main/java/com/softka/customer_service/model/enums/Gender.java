package com.softka.customer_service.model.enums;

public enum Gender {
    F("FEMENINO"),
    M("MASCULINO");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
