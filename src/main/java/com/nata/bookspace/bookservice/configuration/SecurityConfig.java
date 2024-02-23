package com.nata.bookspace.bookservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/register/").permitAll()
                .requestMatchers(HttpMethod.GET, "/books/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/feedbacks/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/feedbacks/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/annotation/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN"));

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();

    }


}