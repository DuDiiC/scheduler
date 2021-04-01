package com.md.scheduler.configuration.api.errors;

import java.util.HashMap;
import java.util.Map;

public class EntityNotFoundException extends RuntimeException {

    private final Map<String, Object> rejectedFields;

    public EntityNotFoundException(Class<?> clazz, Map<String, Object> rejectedFields) {
        super(clazz.getSimpleName().toUpperCase() + " was not found");
        this.rejectedFields = rejectedFields;
    }

    public Map<String, Object> getRejectedFields() {
        return new HashMap<>(rejectedFields);
    }
}
