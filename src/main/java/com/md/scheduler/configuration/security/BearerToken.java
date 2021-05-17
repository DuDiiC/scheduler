package com.md.scheduler.configuration.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class BearerToken {

    @JsonProperty("Bearer")
    private String bearer;
}
