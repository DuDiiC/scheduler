package com.md.scheduler.users;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);

    void delete(User entity);
}
