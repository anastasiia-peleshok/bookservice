package com.nata.bookspace.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;

    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", email='" + email + '\'' + '}';
    }
}
