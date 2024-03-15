package com.nata.bookspace.bookservice.entity;

//import com.nata.bookspace.bookservice.lib.ValidEmail;
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
  //  private boolean enabled;
    @Column(unique = true)
    private String email;

    private String password;
    @ManyToMany(cascade = {CascadeType.REMOVE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", email='" + email + '\'' + '}';
    }
}
