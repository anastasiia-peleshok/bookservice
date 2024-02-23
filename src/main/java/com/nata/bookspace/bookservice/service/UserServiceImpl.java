package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.mapper.UserMapper;
import com.nata.bookspace.bookservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public List<ResponseUserDTO> getUsers() {
        List<User> userList=userRepository.findAll();
        List<ResponseUserDTO> responseUserDtoList =userList.stream().map(a->UserMapper.mapToUserDTO(a)).collect(Collectors.toList());
        return responseUserDtoList;
    }


    @Override
    public Optional<User> getUserById(long theId) {
        if (userRepository.findById(theId).isEmpty()) {
            throw new IllegalArgumentException("User with id" + theId + " is not exists");
        }
        return userRepository.findById(theId);
    }

    @Override
    public Optional<User> getUserByEmail(String theEmail) {
        if (userRepository.findUserByEmail(theEmail).isEmpty()) {
            throw new IllegalArgumentException("User with email" + theEmail + " is not exists");
        }
        return userRepository.findUserByEmail(theEmail);
    }

    @Override
    @Transactional
    public User saveUser(User theUser) {
        if (userRepository.findUserByEmail(theUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + theUser.getEmail() + " already exists");
        }
        return userRepository.save(theUser);
    }

    @Override
    @Transactional
    public void deleteUser(long theId) {
        if (userRepository.findById(theId).isEmpty()) {
            throw new IllegalArgumentException("User with id" + theId + " is not found");
        }
        userRepository.deleteById(theId);
    }
//
//    @Override
//    public User updateUser(long theId, User updatedUser) {
//        return null;
//    }
}
