package com.md.scheduler.configuration.api.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class EntityNotFoundDetails implements ApiErrorDetails {

    private final String field;
    private final Object notFoundValue;
}
