package com.md.scheduler.users;

import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserInfo getById(long id) throws EntityNotFoundException {
        return new UserInfo(repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(User.class, Map.of("id", id))
                )
        );
    }
}
