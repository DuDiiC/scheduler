package com.md.scheduler.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
interface UserController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable Long id);
}
