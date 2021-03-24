package com.md.scheduler.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.md.scheduler.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtConfigProperties jwtConfigProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User principal = (User) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfigProperties.getExpirationTime()))
                .sign(Algorithm.HMAC256(jwtConfigProperties.getSecret()));
//        response.addHeader(jwtConfigProperties.getTokenHeader(), jwtConfigProperties.getTokenPrefix() + token); // version with set bearer in header
//        response.getWriter().write(jwtConfigProperties.getTokenPrefix() + token); // version with bearer in response body
//        response.getWriter().flush();
        response.setContentType("application/json");
        response.getWriter().print("{\"" + jwtConfigProperties.getTokenPrefix().replace(" ", "") + "\": \"" + token + "\"}");
    }
}
