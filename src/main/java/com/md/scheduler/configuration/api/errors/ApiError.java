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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;
    private final String status;
    private final String message;
    private List<ApiErrorDetails> details;

    public void addValidationErrorDetails(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationErrorDetail);
    }

    public void addValidationErrorDetail(List<ObjectError> objectErrors) {
        objectErrors.forEach(this::addValidationErrorDetail);
    }

    public void addEntityNotFoundExceptions(Map<String, Object> rejectedValues) {
        rejectedValues.forEach(this::addEntityNotFoundException);
    }

    private void addErrorDetail(ApiErrorDetails ex) {
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        details.add(ex);
    }

    private void addValidationErrorDetail(String object, String message) {
        this.addErrorDetail(new ValidationErrorDetails(object, message));
    }

    private void addValidationErrorDetail(String object, String field, Object rejectedValue, String message) {
        this.addErrorDetail(new ValidationErrorDetails(object, field, rejectedValue, message));
    }

    private void addValidationErrorDetail(FieldError fieldError) {
        this.addValidationErrorDetail(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
        );
    }

    private void addValidationErrorDetail(ObjectError objectError) {
        this.addValidationErrorDetail(
                objectError.getObjectName(),
                objectError.getDefaultMessage()
        );
    }

    private void addEntityNotFoundException(String key, Object value) {
        this.addErrorDetail(new EntityNotFoundDetails(key, value));
    }
}
