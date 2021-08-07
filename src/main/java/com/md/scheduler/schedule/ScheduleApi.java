package com.md.scheduler.schedule;

import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/v1/schedules")
@Tag(name = "schedule")
interface ScheduleApi {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    List<ScheduleResponse> getAll();

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    ScheduleResponse getById(@PathVariable Long id) throws EntityNotFoundException;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    ScheduleResponse save(@RequestBody @Valid NewSchedule newSchedule, Principal principal)
            throws UsernameNotFoundException, ResourceAlreadyExistsException;

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    void delete(@PathVariable Long id, Principal principal)
            throws UsernameNotFoundException, EntityNotFoundException, AccessDeniedException;
}
