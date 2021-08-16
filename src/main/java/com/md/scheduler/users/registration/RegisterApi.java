package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.users.UserInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@Tag(name = "registration", description = "registration API to create new user")
interface RegisterApi {

    @PostMapping("/register")
    UserInfo register(@RequestBody @Valid NewUser newUser) throws ResourceAlreadyExistsException;
}
