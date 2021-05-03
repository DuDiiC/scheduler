package com.md.scheduler.configuration.api.errors;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PACKAGE)
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
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
