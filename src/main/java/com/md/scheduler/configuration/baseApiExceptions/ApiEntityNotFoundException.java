package com.md.scheduler.configuration.baseApiExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class ApiEntityNotFoundException implements ApiSubException {

    private final String field;
    private final Object notFoundValue;
}
