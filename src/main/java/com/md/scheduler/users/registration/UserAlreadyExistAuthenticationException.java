package com.md.scheduler.users.registration;

import javax.naming.AuthenticationException;

class UserAlreadyExistAuthenticationException extends AuthenticationException {

    UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }
}
