package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.entity.Role;
import com.nata.bookspace.bookservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public Role save(Role role) {
        if(roleRepository.findByRoleName((role.getRoleName())).isPresent()){
            throw new IllegalArgumentException("Role with this name  " + role.getRoleName().toString() + " already exists");

        }
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByRoleName(Role.RoleName.valueOf(roleName)).orElseThrow(
                () -> new NoSuchElementException("Can't find role by role name:" + roleName));
    }
}
