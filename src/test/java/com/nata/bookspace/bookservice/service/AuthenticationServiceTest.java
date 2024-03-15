package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.entity.Role;
import com.nata.bookspace.bookservice.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void testGrantAdmin_UserExists() {
        String userEmail = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(userEmail);
        user.setRoles(new HashSet<>());

        Role adminRole = new Role(1L, "ADMIN");

        given(userService.getUserByEmail(userEmail)).willReturn(Optional.of(user));
        given(roleService.getByName("ADMIN")).willReturn(adminRole);
        given(userService.updateUser(user.getId(), user)).willReturn(user);

        User result = authenticationService.grantAdmin(userEmail);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertTrue(result.getRoles().contains(adminRole));
    }

    @Test
    public void testGrantAdmin_UserNotFound() {
        String userEmail = "nonexisting@example.com";

        given(userService.getUserByEmail(userEmail)).willReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.grantAdmin(userEmail));
    }

    @Test
    public void testGrantAdmin_RoleNotFound() {
        String userEmail = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(userEmail);
        user.setRoles(new HashSet<>());

        given(userService.getUserByEmail(userEmail)).willReturn(Optional.of(user));
        given(roleService.getByName("ADMIN")).willThrow(new NoSuchElementException("Role with name ADMIN not found"));

        assertThrows(NoSuchElementException.class, () -> authenticationService.grantAdmin(userEmail));
    }

    @Test
    public void testRegister_NewUser() {
        String userEmail = "test@example.com";
        String userPassword = "password";

        User newUser = new User();
        newUser.setId(1L);
        newUser.setEmail(userEmail);
        String encodedPassword = "encodedPassword";
        newUser.setPassword(encodedPassword);

        Role userRole = new Role(1L, "USER");

        given(roleService.getByName("USER")).willReturn(userRole);
        given(passwordEncoder.encode(userPassword)).willReturn(encodedPassword);

        newUser.setRoles(Collections.singleton(userRole));

        given(userService.saveUser(any(User.class))).willReturn(newUser);

        User result = authenticationService.register(userEmail, userPassword);

        assertNotNull(result);
        assertEquals(newUser.getId(), result.getId());
        assertEquals(userEmail, result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getRoles().contains(userRole));
    }


    @Test
    public void testRegister_UserAlreadyExists() {
        String userEmail = "existing@example.com";
        String userPassword = "password";

        given(userService.saveUser(any(User.class))).willThrow(new IllegalArgumentException("User with email " + userEmail + " already exists"));

        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(userEmail, userPassword));
    }

    @Test
    public void testRegister_RoleNotFound() {
        String userEmail = "test@example.com";
        String userPassword = "password";

        given(roleService.getByName("USER")).willThrow(new NoSuchElementException("Role with name USER not found"));

        assertThrows(NoSuchElementException.class, () -> authenticationService.register(userEmail, userPassword));
    }
}
