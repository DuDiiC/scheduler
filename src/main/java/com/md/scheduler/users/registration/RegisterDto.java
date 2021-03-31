package com.md.scheduler.users.registration;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
class RegisterDto {

    @Email(message = "Email must be in correct format")
    String email;

    @Size(
            min = 5, max = 100,
            message = "Username must be in correct size (between 5 and 100 chars"
    )
    String username;

    @Password(
            minLength = 8,
            maxLength = 24,
            uppercaseCharRequired = true,
            lowercaseCharRequired = true,
            digitRequired = true,
            specialCharRequired = true,
            message = "The password must be 8-24 characters long, with at least one lowercase, " +
                "one uppercase letter, one number and one special character"
    )
    String password;
}
