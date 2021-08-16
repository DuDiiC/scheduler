package com.md.scheduler.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.scheduler.configuration.api.errors.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        try (ServletServerHttpResponse res = new ServletServerHttpResponse(response)) {
            res.setStatusCode(HttpStatus.FORBIDDEN);
            res.getServletResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            res.getBody().write(mapper.writeValueAsString(
                    ApiError.builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.FORBIDDEN.toString())
                            .message("\uD835\uDD50\uD835\uDD60\uD835\uDD66 \uD835\uDD64\uD835\uDD59\uD835\uDD52\uD835\uDD5D\uD835\uDD5D \uD835\uDD5F\uD835\uDD60\uD835\uDD65 \uD835\uDD61\uD835\uDD52\uD835\uDD64\uD835\uDD64")
                            .build()
            ).getBytes());
        }
    }
}
