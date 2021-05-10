package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.User;
import com.md.scheduler.users.UserInfo;
import com.md.scheduler.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterServiceImpl implements RegisterService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo register(RegisterDto newUser) throws UserAlreadyExistAuthenticationException {
        checkUserUnique(newUser);

        return new UserInfo(
                repository.save(new User(
                        newUser.username,
                        passwordEncoder.encode(newUser.password),
                        newUser.email,
                        AppUserRole.ROLE_USER,
                        true
                ))
        );
    }

    private void checkUserUnique(RegisterDto user) throws UserAlreadyExistAuthenticationException {
        checkUsernameUnique(user.username);
        checkEmailUnique(user.email);
    }

    private void checkUsernameUnique(String username) throws UserAlreadyExistAuthenticationException {
        if (repository.existsByUsername(username)) {
            throw new UserAlreadyExistAuthenticationException("User with username " + username + " already exists");
        }
    }

    private void checkEmailUnique(String email) throws UserAlreadyExistAuthenticationException {
        if (repository.existsByEmail(email)) {
            throw new UserAlreadyExistAuthenticationException("User with email " + email + " already exists");
        }
    }
}
