package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.api.errors.ApiError;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.users.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@Tag(name = "registration", description = "registration API to create new user")
interface RegisterApi {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "register a new user in system with role \"USER\"",
            description = "Register new user in system with role \"USER\".")
    @ApiResponse(responseCode = "201", description = "Successfully created.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfo.class)))
    @ApiResponse(responseCode = "400", description = "Error reported during the validation of the request body",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "User with selected username or email already exists.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    UserInfo register(@RequestBody @Valid @Parameter(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewUser.class)),
            description = "New user to create"
    ) NewUser newUser) throws ResourceAlreadyExistsException;
}
