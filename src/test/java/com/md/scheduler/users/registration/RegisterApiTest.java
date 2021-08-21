package com.md.scheduler.users.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        @Nested
        @DisplayName("username")
        class UsernameTest {

            @ParameterizedTest
            @DisplayName("Valid cases")
            @ValueSource(strings = {"user1", "1user", "1USER1", "ourFirstUser", "veryLongUserNameButStillShorterThan50"})
            void shouldBeValidUsername(String username) throws Exception {
                String newValidUser = String.format("""
                        {
                            "email": "email@email.com",
                            "username": "%s",
                            "password": "Password1?"
                        }
                        """, username);
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
                        .content(newValidUser))
                        .andDo(print())
                        .andExpect(status().isCreated());
            }

            @ParameterizedTest
            @DisplayName("Invalid cases")
            @ValueSource(strings = {"", "user", "user!", "1_USER_1", "ourFirst?User",
                    "veryLongUserNaaaaaaaaaaaaaaaaaaaaaaaaaaaameLongerThan50"})
            void shouldBeInvalidUsername(String username) throws Exception {
                String newValidUser = String.format("""
                        {
                            "email": "email@email.com",
                            "username": "%s",
                            "password": "Password1?"
                        }
                        """, username);

                mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newValidUser))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$").exists())
                        .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                        .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.toString())))
                        .andExpect(jsonPath("$..field", hasItem("username")));
            }
        }

        @Nested
        @DisplayName("password")
        class PasswordTest {

            @ParameterizedTest
            @DisplayName("Valid cases")
            @ValueSource(strings = {"Password1!", "myGoodpass.!1"})
            void shouldBeValidPassword(String password) throws Exception {
                String newValidUser = String.format("""
                        {
                            "email": "email@email.com",
                            "username": "username",
                            "password": "%s"
                        }
                        """, password);
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
                        .content(newValidUser))
                        .andDo(print())
                        .andExpect(status().isCreated());
            }

            @ParameterizedTest
            @DisplayName("Invalid cases")
            @ValueSource(strings = {
                    "onlylowercaseletters", "ONLYUPPERCASELETTERS", "OnlyLetters",
                    "123456789", "OnlyLettersAndNum123", "OnlylettersAndSpecials!?-",
                    "?!-deas22"
            })
            void shouldBeInvalidPassword(String password) throws Exception {
                String newValidUser = String.format("""
                        {
                            "email": "email@email.com",
                            "username": "username",
                            "password": "%s"
                        }
                        """, password);

                mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newValidUser))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$").exists())
                        .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                        .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.toString())))
                        .andExpect(jsonPath("$..field", hasItem("password")));
            }
        }
    }
}
