package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ResponseUserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email).get());
    }


    @GetMapping("{userId}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId).get());
    }

//    @PostMapping()
//    @ResponseBody
//    public ResponseEntity<User> saveUser(@RequestBody User theUser) {
//        return ResponseEntity.ok(userService.saveUser(theUser));
//    }

//    @DeleteMapping("{userId}")
//    @ResponseBody
//    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.ok().build();
//    }
}
