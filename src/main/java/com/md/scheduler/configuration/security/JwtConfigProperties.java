package com.md.scheduler.configuration.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("jwt")
@Configuration
class JwtConfigProperties {

    private String secret;
    private Long expirationTime;
    private String tokenHeader;
    private String tokenPrefix;

    String getSecret() {
        return secret;
    }

    void setSecret(String secret) {
        this.secret = secret;
    }

    Long getExpirationTime() {
        return expirationTime;
    }

    void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    String getTokenHeader() {
        return tokenHeader;
    }

    void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    String getTokenPrefix() {
        return tokenPrefix;
    }

    void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
