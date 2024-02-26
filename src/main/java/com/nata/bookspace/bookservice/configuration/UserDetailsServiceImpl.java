package com.nata.bookspace.bookservice.configuration;

import com.nata.bookspace.bookservice.entity.User;
import com.nata.bookspace.bookservice.repository.UserRepository;
import com.nata.bookspace.bookservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        if(userRepository.findUserByEmail(useremail).isEmpty()){
            throw new UsernameNotFoundException("User with email "+useremail+" is not found");
        }
        User user=userRepository.findUserByEmail(useremail).get();

        org.springframework.security.core.userdetails.User newUser= new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());


        return newUser;}
}
