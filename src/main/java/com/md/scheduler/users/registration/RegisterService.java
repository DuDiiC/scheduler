package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.User;
import com.md.scheduler.users.UserInfo;
import com.md.scheduler.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class RegisterService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserInfo register(NewUser newUser) throws UserAlreadyExistAuthenticationException {
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

    private void checkUserUnique(NewUser newUser) throws UserAlreadyExistAuthenticationException {
        checkUsernameUnique(newUser.username);
        checkEmailUnique(newUser.email);
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
