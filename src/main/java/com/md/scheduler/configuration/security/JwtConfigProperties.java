package com.md.scheduler.configuration.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("jwt")
@Configuration
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
class JwtConfigProperties {

    private String secret;
    private Long expirationTime;
    private String tokenHeader;
    private String tokenPrefix;
}
