package com.md.scheduler.schedule;

import com.md.scheduler.configuration.api.errors.ApiError;
import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.configuration.swagger.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/v1/schedules")
@Tag(name = "schedule", description = "requests to manage schedules")
interface ScheduleApi {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "returns all schedulers in database",
            description = "Returns all schedulers in database. Operation available only to the administrator.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponse(responseCode = "200", description = "Successful operation.",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScheduleResponse.class))))
    @ApiResponse(responseCode = "401", description = "Operation available only to the authenticated user.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "403", description = "Operation available only for the administrator.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @PageableParameter
    List<ScheduleResponse> getAll(@Parameter(hidden = true) Pageable pageable);

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "returns selected schedule by ID",
            description = "Returns selected schedule by ID. Operation available only for the schedule owner.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponse(responseCode = "200", description = "Successful operation.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponse.class)))
    @ApiResponse(responseCode = "401", description = "Operation available only to the authenticated user.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "403", description = "Operation available only for the schedule owner.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "The object with the specified ID does not exist in the system.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    ScheduleResponse getById(@PathVariable @Parameter(description = "Schedule ID to fetch.", example = "1") Long id, @NotNull Principal principal)
            throws EntityNotFoundException, UsernameNotFoundException, AccessDeniedException;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "creates a new schedule owned by the user making the request",
            description = "Creates a new schedule owned by the user making the request.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponse(responseCode = "201", description = "Successfully created.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error reported during the validation of the request body or user not found based on authorization data.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "401", description = "Operation available only to the authenticated user.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "Each user's schedule must have a unique name.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    ScheduleResponse save(
            @RequestBody @Valid @Parameter(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewSchedule.class)),
                    description = "New schedule to create."
            ) NewSchedule newSchedule, @NotNull Principal principal
    ) throws ResourceAlreadyExistsException;

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "removes existing schedule",
            description = "Removes existing schedule. Operation available only for schedule owner.",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponse(responseCode = "200", description = "Successfully removed.")
    @ApiResponse(responseCode = "401", description = "Operation available only to the authenticated user.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "403", description = "Operation available only for the schedule owner.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "The object with the specified ID does not exist in the system.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    void delete(@PathVariable @Parameter(description = "Schedule ID to remove.", example = "1") Long id, @NotNull Principal principal)
            throws EntityNotFoundException, AccessDeniedException;
}
