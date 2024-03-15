package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.Role;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        userList.add(user1);
        userList.add(user2);

        given(userRepository.findAll()).willReturn(userList);

        List<ResponseUserDTO> responseUserDTOList = userService.getUsers();

        assertNotNull(responseUserDTOList);
        assertEquals(responseUserDTOList.size(),2);

        ResponseUserDTO firstUserDTO = responseUserDTOList.get(0);
        assertEquals(firstUserDTO.getId(),1L);

        ResponseUserDTO secondUserDTO = responseUserDTOList.get(1);
        assertEquals(secondUserDTO.getId(),2L);

    }
    @Test
    public void testGetUsersEmptyList() {
        List<User> emptyList = new ArrayList<>();
        given(userRepository.findAll()).willReturn(emptyList);

        List<ResponseUserDTO> responseUserDTOList = userService.getUsers();

        assertNotNull(responseUserDTOList);
        assertTrue(responseUserDTOList.isEmpty());
    }
    @Test
    public void testGetUserById() {

        long userId = 1L;
        User user = new User();
        user.setId(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(userId);

        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testGetUserByIdNotFound() {
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userService.getUserById(userId)
        );

        assertEquals("User with id " + userId + " is not exists", exception.getMessage());
    }
    @Test
    public void testGetUserByEmail() {
        String email = "john.doe@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        given(userRepository.findUserByEmail(email)).willReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail(email);

        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        String email = "nonexistent@example.com";
        given(userRepository.findUserByEmail(email)).willReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userService.getUserByEmail(email)
        );

        assertEquals("User with email " + email + " is not exists", exception.getMessage());
    }

    @Test
    public void testGetUserByEmailEmptyEmail() {
        String email = "";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserByEmail(email)
        );

        assertEquals("Email cannot be empty", exception.getMessage());
        verify(userRepository, times(0)).findUserByEmail(email);
    }

    @Test
    public void testGetUserByEmailNullEmail() {
        String email = null;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserByEmail(email)
        );

        assertEquals("Email cannot be empty", exception.getMessage());
        verify(userRepository, times(0)).findUserByEmail(email);
    }
    @Test
    public void testSaveUser() {
        User newUser = new User();
        newUser.setEmail("john.doe@example.com");
        newUser.setPassword("john.doe");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("john.doe@example.com");
        savedUser.setPassword("john.doe");

        given(userRepository.findUserByEmail(newUser.getEmail())).willReturn(Optional.empty());
        given(userRepository.save(newUser)).willReturn(savedUser);

        User result = userService.saveUser(newUser);

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getEmail(), result.getEmail());
        assertEquals(savedUser.getPassword(), result.getPassword());
        verify(userRepository, times(1)).findUserByEmail(newUser.getEmail());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testSaveUserEmailExists() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("john.doe");

        given(userRepository.findUserByEmail(existingUser.getEmail())).willReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.saveUser(existingUser)
        );

        assertEquals("User with email " + existingUser.getEmail() + " already exists", exception.getMessage());
        verify(userRepository, times(1)).findUserByEmail(existingUser.getEmail());
        verify(userRepository, times(0)).save(any(User.class));
    }
    @Test
    public void testUpdateUser() {
        Role userRole=new Role();
        userRole.setRoleName("USER");
        Role adminRole=new Role();
        adminRole.setRoleName("ADMIN");
        long userId = 1L;
        User existingUser = new User(userId, "john.doe@example.com", "password", Set.of(userRole));
        User updatedUser = new User(userId, "jane.smith@example.com", "newpassword", Set.of(userRole,adminRole));
        User savedUser = new User(userId, "jane.smith@example.com", "newpassword",Set.of(userRole,adminRole));

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));
        given(userRepository.save(existingUser)).willReturn(savedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getRoles(), result.getRoles());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUserNotFound() {
        Role userRole=new Role();
        userRole.setRoleName("USER");

        long userId = 1L;
        User updatedUser = new User(userId, "john.doe@example.com", "password", Set.of(userRole));

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userService.updateUser(userId, updatedUser)
        );

        assertEquals("User with id " + userId + " is not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        Role userRole=new Role();
        userRole.setRoleName("USER");
        long userId = 1L;
        User existingUser = new User(userId, "john.doe@example.com", "password", Set.of(userRole));

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserNotFound() {
        long userId = 1L;

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userService.deleteUser(userId)
        );

        assertEquals("User with id " + userId + " is not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).deleteById(userId);
    }
}

