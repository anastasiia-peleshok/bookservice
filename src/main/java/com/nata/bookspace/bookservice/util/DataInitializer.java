//package com.nata.bookspace.bookservice.util;
//
//import com.nata.bookspace.bookservice.entity.Role;
//import com.nata.bookspace.bookservice.entity.User;
//import com.nata.bookspace.bookservice.service.RoleServiceImpl;
//import com.nata.bookspace.bookservice.service.UserServiceImpl;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer {
//    private final RoleServiceImpl roleService;
//    private final UserServiceImpl userService;
//
//
//
//    @PostConstruct
//    public void inject() {
//        Role adminRole = roleService.getByName("ADMIN");
//        User user = new User();
//        user.setEmail("admin@i.ua");
//        user.setPassword("admin123");
//        user.setRoles(Set.of(adminRole));
//        userService.saveUser(user);
//    }
//}
