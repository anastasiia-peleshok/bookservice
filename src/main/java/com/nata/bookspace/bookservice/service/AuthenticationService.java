package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.entity.User;

public interface AuthenticationService {
    User register(String email, String password);
    User grantAdmin(String email);
    void deleteUser(long theId);

}