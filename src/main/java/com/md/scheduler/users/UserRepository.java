package com.md.scheduler.users;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);
}
