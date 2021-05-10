package com.md.scheduler.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.User;
import com.md.scheduler.users.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthTest {

    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    private static final String LOGIN_URL = "/api/v1/login";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private static AuthCredentials authDto;


    @BeforeAll
    static void initLoginDto() {
        authDto = new AuthCredentials(USER_NAME, USER_PASSWORD);
    }

    @Test
    void withoutAuthorization_loginRequest_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post(LOGIN_URL).content(""))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @Transactional
    void withAuthorizationByUser_loginRequest_shouldReturnOk() throws Exception {
        createTestUser();

        mockMvc.perform(post(LOGIN_URL)
                .content(mapper.writeValueAsString(authDto)))
                .andExpect(status().is(HttpStatus.OK.value()));

        deleteTestUser();
    }

    @Test
    @Transactional
    void withAuthorizationByUser_loginRequest_shouldReturnToken() throws Exception {
        createTestUser();

        JsonNode response = mapper.readTree(mockMvc.perform(post(LOGIN_URL)
                .content(mapper.writeValueAsString(authDto)))
                .andReturn()
                .getResponse()
                .getContentAsString());

        assertNotNull(response.get("Bearer"));

        deleteTestUser();
    }

    @Test
    @Transactional
    void withAuthorizedByUser_loginRequest_shouldReturnValidToken() throws Exception {
        createTestUser();

        String token = mapper.readTree(mockMvc.perform(post(LOGIN_URL)
                .content(mapper.writeValueAsString(authDto)))
                .andReturn()
                .getResponse()
                .getContentAsString()).get("Bearer").toString();
        DecodedJWT verifiedToken = JWT.decode(token);

        assertEquals(USER_NAME, verifiedToken.getSubject());

        deleteTestUser();
    }

    private void createTestUser() {
        User givenUser = new User(
                USER_NAME, passwordEncoder.encode(USER_PASSWORD),
                "email", AppUserRole.ROLE_USER, true);
        userRepository.save(givenUser);
    }

    private void deleteTestUser() {
        userRepository.delete(userRepository.findByUsername(USER_NAME).orElseThrow());
    }
}
