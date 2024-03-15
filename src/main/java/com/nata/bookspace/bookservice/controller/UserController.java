package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<ResponseUserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email).get());
    }


    @GetMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId).get());
    }
    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
