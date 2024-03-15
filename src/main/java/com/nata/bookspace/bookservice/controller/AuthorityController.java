package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.mapper.UserMapper;
import com.nata.bookspace.bookservice.service.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authority/")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthenticationServiceImpl authService;

    @PostMapping("grantAdminAuthority")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUserDTO> grantAdminAuthority(@RequestParam String theEmail) {
        User user = authService.grantAdmin(theEmail);
        return ResponseEntity.ok(UserMapper.mapToUserDTO(user));
    }
}

