package com.nata.bookspace.bookservice.controller;

import com.nata.bookspace.bookservice.config.AuthRequest;
import com.nata.bookspace.bookservice.config.JwtService;
import com.nata.bookspace.bookservice.config.TokenBlacklist;
import com.nata.bookspace.bookservice.dto.RequestUserDTO;
import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.mapper.UserMapper;
import com.nata.bookspace.bookservice.service.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




@RestController
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationServiceImpl authService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final TokenBlacklist tokenBlacklist;

    @PostMapping("/login/")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            return token;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    @PostMapping("/logout/")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            tokenBlacklist.addToBlacklist(token);
        }
        return ResponseEntity.ok("Logged out successfully.");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
//    @PostMapping("/login/")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.getUsername());
//        } else {
//            throw new UsernameNotFoundException("invalid user request !");
//        }
//    }
//    @PostMapping("/logout/")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            securityContextLogoutHandler.logout(request, response, authentication);
//        }
//        return ResponseEntity.ok("Logged out successfully.");
//    }
    @PostMapping("/register/")
    public ResponseEntity<ResponseUserDTO> register(@Valid @RequestBody RequestUserDTO userDTO) {
        User user = authService.register(userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.ok(UserMapper.mapToUserDTO(user));
    }

    @DeleteMapping ("/{user_id}")
    public ResponseEntity<ResponseUserDTO> register(@PathVariable long user_id) {
        authService.deleteUser(user_id);
        return ResponseEntity.ok().build();
    }
}
