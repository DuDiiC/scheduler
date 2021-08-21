package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
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
    public UserInfo register(NewUser newUser) throws ResourceAlreadyExistsException {

        if (repository.existsByEmail(newUser.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + newUser.getEmail() + " already exists");
        } else if (repository.existsByUsername(newUser.getUsername())) {
            throw new ResourceAlreadyExistsException("User with username " + newUser.getUsername() + " already exists");
        }

        return new UserInfo(
                repository.save(new User(
                        newUser.getUsername(),
                        passwordEncoder.encode(newUser.getPassword()),
                        newUser.getEmail(),
                        AppUserRole.ROLE_USER,
                        true
                ))
        );
    }
}
