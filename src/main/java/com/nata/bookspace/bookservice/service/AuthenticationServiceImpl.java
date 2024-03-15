package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.entity.Role;
import com.nata.bookspace.bookservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User grantAdmin(String email) {
        User user= userService.getUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email"+ email + "not found"));

        Set<Role> roles= user.getRoles();
        roles.add(roleService.getByName("ADMIN"));

        user.setRoles(roles);

        user = userService.updateUser(user.getId(),user);

        return user;
    }

    @Override
    @Transactional
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByName("USER"));
        user.setRoles(roles);

        user = userService.saveUser(user);

        return user;
    }
    @Override
    @Transactional
    public void deleteUser(long theId) {
        userService.deleteUser(theId);
    }
}
