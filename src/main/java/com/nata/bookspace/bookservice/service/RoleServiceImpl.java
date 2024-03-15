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
            throw new IllegalArgumentException("Role with name " + role.getRoleName() + " is already exist");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new NoSuchElementException("Can't find role with name:" + roleName));
    }
}
