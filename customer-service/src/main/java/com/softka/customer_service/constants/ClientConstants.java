package com.softka.customer_service.constants;

public class ClientConstants {

    private ClientConstants() {
    }

    // Messages
    public static final String NOT_FOUND = "Not Found";
    public static final String ALREADY_EXIST = "Already exists";
    public static final String INVALID_INPUT_DATA = "Invalid input data";
    public static final String BAD_REQUEST = "Bad request";


    // Messages exception
    public static final String CLIENT_ALREADY_EXIST = "Client already exist with dni %s";
    public static final String CLIENT_NOT_EXIST = "Client does not exist with id %d";
}
