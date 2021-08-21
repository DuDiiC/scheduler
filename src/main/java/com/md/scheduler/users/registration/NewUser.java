package com.md.scheduler.users.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class NewUser {

    @NotNull(message = "email is required")
    @Email(message = "email must be in correct format")
    private String email;

    @NotNull(message = "username is required")
    @Size(
            min = 5, max = 100,
            message = "username must be in correct size (between 5 and 100 chars"
    )
    private String username;

    @NotNull(message = "password is required")
    @Size(
            min = 8, max = 24,
            message = "password must be 8-24 characters long"
    )
    @Pattern(regexp = ".*[a-z].*", message = "password must contains at least one lowercase")
    @Pattern(regexp = ".*[A-Z].*", message = "password must contains at least one lowercase")
    @Pattern(regexp = ".*\\d.*", message = "password must contains at least one digit")
    @Pattern(regexp = ".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*", message = "password must contains at least one special character")
    private String password;
}
