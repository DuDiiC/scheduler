package com.md.scheduler.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
class UserControllerImpl implements UserController {

    private final UserRepository repository;

    @Override
    public ResponseEntity<?> getUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new UserInfo(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
