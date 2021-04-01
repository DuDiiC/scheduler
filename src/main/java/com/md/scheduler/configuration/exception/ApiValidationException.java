package com.md.scheduler.configuration.exception;

import lombok.Getter;

@Getter
class ApiValidationException implements ApiSubException {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationException(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ApiValidationException(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
