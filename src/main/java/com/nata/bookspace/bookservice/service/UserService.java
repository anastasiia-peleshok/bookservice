package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<ResponseUserDTO> getUsers();
    Optional<User> getUserById(long theId);
    Optional<User> getUserByEmail(String theEmail);
    User saveUser(User theUser);
    User updateUser(long theId,User updatedUser);
    void deleteUser(long theId);
}
