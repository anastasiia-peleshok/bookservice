package com.nata.bookspace.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.toString();
    }

    public String getRoleName() {
        return roleName.toString();
    }

    public enum RoleName {
        ADMIN, USER;

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
