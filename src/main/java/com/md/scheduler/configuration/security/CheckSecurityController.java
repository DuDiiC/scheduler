package com.md.scheduler.configuration.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
class CheckSecurityController {

    public static final String CONTENT = "content";

    @PreAuthorize("permitAll()")
    @GetMapping("/unsecured")
    public String unsecured() {
        return CONTENT;
    }

    @GetMapping("/securedForAuthenticatedUser")
    @PreAuthorize("isAuthenticated()")
    public String securedForAuthenticatedUser() {
        return CONTENT;
    }

    @GetMapping("/securedForAdminOnly")
    @PreAuthorize("hasRole('ADMIN')")
    public String securedForAdminOnly() {
        return CONTENT;
    }
}
