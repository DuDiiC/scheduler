package com.md.scheduler.users;

import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
@Tag(name = "user")
interface UserApi {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    UserInfo getUser(@PathVariable Long id) throws EntityNotFoundException;
}
