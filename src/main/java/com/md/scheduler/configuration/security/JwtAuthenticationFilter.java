package com.md.scheduler.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.md.scheduler.users.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtConfigProperties jwtConfigProperties;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserDetailsService userDetailsService,
                                   JwtConfigProperties jwtConfigProperties) {
        super(authenticationManager);
        this.jwtConfigProperties = jwtConfigProperties;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        if (authenticationToken == null) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtConfigProperties.getTokenHeader());
        if(token != null && token.startsWith(jwtConfigProperties.getTokenPrefix())) {
            String username = JWT.require(Algorithm.HMAC256(jwtConfigProperties.getSecret()))
                    .build()
                    .verify(token.replace(jwtConfigProperties.getTokenPrefix(), ""))
                    .getSubject();
            if(username != null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getAuthorities(), userDetails.getAuthorities());
            }
        }
        return null;
    }
}
