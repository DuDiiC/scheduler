package com.md.scheduler.configuration.api.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(oneOf = {EntityNotFoundDetails.class, ValidationErrorDetails.class})
interface ApiErrorDetails {
}
