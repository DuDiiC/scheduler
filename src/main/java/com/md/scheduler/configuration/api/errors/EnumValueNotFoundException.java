package com.md.scheduler.configuration.api.errors;

public class EnumValueNotFoundException extends RuntimeException {

    public EnumValueNotFoundException(String message) {
        super("Not found enum value: " + message);
    }
}
