package com.md.scheduler.users.registration;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
class RegisterDto {

    @NotNull
    @Email(message = "Email must be in correct format")
    String email;

    @NotNull
    @Size(
            min = 5, max = 100,
            message = "Username must be in correct size (between 5 and 100 chars"
    )
    String username;

    @NotNull
    @Size(
            min = 8, max = 24,
            message = "password must be 8-24 characters long"
    )
    @Pattern.List({
            @Pattern(regexp = ".*[a-z].*", message = "password must contains at least one lowercase"),
            @Pattern(regexp = ".*[A-Z].*", message = "password must contains at least one lowercase"),
            @Pattern(regexp = ".*\\d.*", message = "password must contains at least one digit"),
            @Pattern(regexp = ".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*", message = "password must contains at least one special character")
    })
    String password;
}
