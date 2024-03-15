package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.mapper.UserMapper;
import com.nata.bookspace.bookservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<ResponseUserDTO> getUsers() {
        List<User> userList = userRepository.findAll();
        List<ResponseUserDTO> responseUserDtoList = userList.stream().map(a -> UserMapper.mapToUserDTO(a)).collect(Collectors.toList());
        return responseUserDtoList;
    }


    @Override
    public Optional<User> getUserById(long theId) {
        if (userRepository.findById(theId).isEmpty()) {
            throw new NoSuchElementException("User with id " + theId + " is not exists");
        }
        return userRepository.findById(theId);
    }

    @Override
    public Optional<User> getUserByEmail(String theEmail) {
        if(theEmail==null||theEmail==""){
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (userRepository.findUserByEmail(theEmail).isEmpty()) {
            throw new NoSuchElementException("User with email " + theEmail + " is not exists");
        }
        return userRepository.findUserByEmail(theEmail);
    }

    @Override
    @Transactional
    public User saveUser(User theUser) {

        if(userRepository.findUserByEmail(theUser.getEmail()).isPresent()) {
        throw new IllegalArgumentException("User with email " + theUser.getEmail() + " already exists");
        }
        return userRepository.save(theUser);
    }

    @Override
    @Transactional
    public User updateUser(long theUserId,User updatedUser) {
        User existingUser = userRepository.findById(theUserId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + theUserId + " is not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRoles(updatedUser.getRoles());

        return userRepository.save(existingUser);

    }

    @Override
    @Transactional
    public void deleteUser(long theUserId) {
        userRepository.findById(theUserId).orElseThrow(() -> new NoSuchElementException("User with id " + theUserId + " is not found"));
        userRepository.deleteById(theUserId);
    }



}
