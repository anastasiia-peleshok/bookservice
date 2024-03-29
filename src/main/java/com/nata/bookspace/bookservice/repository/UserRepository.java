package com.nata.bookspace.bookservice.repository;

import com.nata.bookspace.bookservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findUserByEmail(String theEmail);

}
