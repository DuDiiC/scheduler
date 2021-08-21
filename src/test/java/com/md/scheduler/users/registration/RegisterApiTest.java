package com.md.scheduler.users.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegisterService service;

    private NewUser newValidUser;

    @BeforeEach
    void setUp() {
        newValidUser = new NewUser(
                "email@email.com",
                "username",
                "Password1?"
        );
    }

    @Test
    @DisplayName("Should return 201 CREATED status and correct JSON after register new user")
    void shouldCreateNewUserCorrectly() throws Exception {
        UserInfo userInfo = new UserInfo(
                1L,
                "email",
                "username",
                AppUserRole.ROLE_USER,
                true
        );
        when(service.register(any(NewUser.class)))
                .thenReturn(userInfo);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newValidUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(userInfo.getId().intValue())))
                .andExpect(jsonPath("$.email", is(userInfo.getEmail())))
                .andExpect(jsonPath("$.username", is(userInfo.getUsername())))
                .andExpect(jsonPath("$.role", is(userInfo.getRole().toString())))
                .andExpect(jsonPath("$.enabled", is(userInfo.isEnabled())));

    }

    @Nested
    @DisplayName("duplications")
    class UserAlreadyExistTest {

        @Test
        @DisplayName("Should return 409 CONFLICT status and correct error JSON when user with selected email already exist")
        void shouldThrowResourceAlreadyExist_whenUserWithSelectedEmailAlreadyExist() throws Exception {
            when(service.register(any(NewUser.class)))
                    .thenThrow(new ResourceAlreadyExistsException("User with email " + newValidUser.getEmail() + " already exists"));

            mvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(newValidUser)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                    .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.toString())))
                    .andExpect(jsonPath("$.message", is(not(emptyString()))))
                    .andExpect(jsonPath("$.details").doesNotExist());
        }

        @Test
        @DisplayName("Should return 409 CONFLICT status and correct error JSON when user with selected username already exist")
        void shouldThrowResourceAlreadyExist_whenUserWithSelectedUsernameAlreadyExist() throws Exception {
            when(service.register(any(NewUser.class)))
                    .thenThrow(new ResourceAlreadyExistsException("User with username " + newValidUser.getUsername() + " already exists"));

            mvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(newValidUser)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                    .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.toString())))
                    .andExpect(jsonPath("$.message", is(not(emptyString()))))
                    .andExpect(jsonPath("$.details").doesNotExist());
        }
    }

    @Nested
    @DisplayName("validation")
    class ValidationTest {

    }
}
