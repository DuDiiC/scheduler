package com.md.scheduler.users.registration;

import com.md.scheduler.users.UserInfo;

import javax.transaction.Transactional;

interface RegisterService {

    @Transactional
    UserInfo register(RegisterDto user) throws UserAlreadyExistAuthenticationException;
}
