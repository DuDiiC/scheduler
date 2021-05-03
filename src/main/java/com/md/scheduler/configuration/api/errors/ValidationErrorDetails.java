package com.md.scheduler.configuration.api.errors;

import lombok.Getter;

@Getter
class ValidationErrorDetails implements ApiErrorDetails {

    private final String object;
    private String field;
    private Object rejectedValue;
    private final String message;

    public ValidationErrorDetails(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ValidationErrorDetails(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        if (field.equals("password")) {
            this.rejectedValue = hidePassword(rejectedValue.toString());
        } else {
            this.rejectedValue = rejectedValue;
        }
        this.message = message;
    }

    private String hidePassword(String password) {
        return "*".repeat(password.length());
    }
}
