package com.md.scheduler.configuration.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthorizedUser_requestForAll_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/test/unsecured"))
                .andExpect(status().is(200))
                .andExpect(content().string("content"));
    }

    @Test
    void unauthorizedUser_requestForAuthorizedUsers_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/test/securedForAuthenticatedUser"))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser
    void authorizedUser_requestForAuthorizedUsers_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/test/securedForAuthenticatedUser"))
                .andExpect(status().is(200))
                .andExpect(content().string("content"));
    }

    @Test
    void unauthorizedUser_requestForAdmin_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/test/securedForAdminOnly"))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser
    void authorizedNonAdminUser_requestForAdmin_shouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/test/securedForAdminOnly"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void admin_requestForAdmin_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/test/securedForAdminOnly"))
                .andExpect(status().is(200))
                .andExpect(content().string("content"));
    }
}
