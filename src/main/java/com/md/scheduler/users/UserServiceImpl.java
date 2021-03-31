package com.md.scheduler.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public User getById(long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with ID = " + id + " not found")
                );
    }
}
