package com.md.scheduler.configuration.api.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:dd:ss")
    private final LocalDateTime timestamp;
    private final String status;
    private final String message;
    private List<ApiErrorDetails> details;

    public void addValidationExceptions(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationException);
    }

    public void addValidationException(List<ObjectError> objectErrors) {
        objectErrors.forEach(this::addValidationException);
    }

    public void addEntityNotFoundExceptions(Map<String, Object> rejectedValues) {
        rejectedValues.forEach(this::addEntityNotFoundException);
    }

    private void addSubException(ApiErrorDetails ex) {
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        details.add(ex);
    }

    private void addValidationException(String object, String message) {
        this.addSubException(new ValidationErrorDetails(object, message));
    }

    private void addValidationException(String object, String field, Object rejectedValue, String message) {
        this.addSubException(new ValidationErrorDetails(object, field, rejectedValue, message));
    }

    private void addValidationException(FieldError fieldError) {
        this.addValidationException(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
        );
    }

    private void addValidationException(ObjectError objectError) {
        this.addValidationException(
                objectError.getObjectName(),
                objectError.getDefaultMessage()
        );
    }

    private void addEntityNotFoundException(String key, Object value) {
        this.addSubException(new EntityNotFoundDetails(key, value));
    }
}
