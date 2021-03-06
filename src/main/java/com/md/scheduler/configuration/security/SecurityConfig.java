package com.md.scheduler.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final ObjectMapper mapper;
    private final RestAuthenticationSuccessHandler authSuccessHandler;
    private final JwtConfigProperties jwtConfigProperties;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().and();
        http
                .authorizeRequests()
                // sign in/sign up
                .antMatchers("/api/v1/register").permitAll()
                .antMatchers("/api/v1/login").permitAll()
                // swagger
                .antMatchers("/swagger-ui/**").permitAll()
                // h2 console
                .antMatchers("/h2-database-console/**").permitAll()
                // H2 console from browser
                .and()
                .headers().frameOptions().disable()
                // stateless session
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // authentication filter
                .and()
                .addFilter(authenticationFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userDetailsService, jwtConfigProperties))
                // handling exceptions
                .exceptionHandling()
                .accessDeniedHandler(new RestAccessDeniedHandler(mapper))
                .authenticationEntryPoint(new RestAuthenticationEntryPoint(mapper));
    }

    JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        var authFilter = new JsonObjectAuthenticationFilter(mapper);
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        authFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
        authFilter.setAuthenticationManager(super.authenticationManager());
        authFilter.setFilterProcessesUrl("/api/v1/login");
        return authFilter;
    }
}
