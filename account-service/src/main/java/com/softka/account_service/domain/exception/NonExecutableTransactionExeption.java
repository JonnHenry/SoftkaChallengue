package com.softka.account_service.domain.exception;

public class NonExecutableTransactionExeption extends RuntimeException {

    public NonExecutableTransactionExeption(String message) {
        super(message);
    }
}
