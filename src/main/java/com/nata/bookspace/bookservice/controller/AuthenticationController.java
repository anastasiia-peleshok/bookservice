package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.RequestUserDTO;
import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.mapper.UserMapper;
import com.nata.bookspace.bookservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;



    @PostMapping("/register/")
    public ResponseEntity<ResponseUserDTO> register(@RequestBody @Valid RequestUserDTO userDTO) {
        User user = authService.register(userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.ok(UserMapper.mapToUserDTO(user));
    }
}
