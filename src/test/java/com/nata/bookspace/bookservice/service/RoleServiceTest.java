package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.entity.Role;
import com.nata.bookspace.bookservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testSaveRole() {
        Role role = new Role();
        role.setRoleName("USER");

        given(roleRepository.findByRoleName(role.getRoleName())).willReturn(Optional.empty());
        given(roleRepository.save(role)).willReturn(role);

        Role savedRole = roleService.save(role);

        assertEquals(role, savedRole);
        verify(roleRepository, times(1)).findByRoleName(role.getRoleName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testSaveRoleAlreadyExists() {
        Role existingRole = new Role();
        existingRole.setRoleName("USER");

        given(roleRepository.findByRoleName(existingRole.getRoleName())).willReturn(Optional.of(existingRole));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> roleService.save(existingRole)
        );

        assertEquals("Role with name " + existingRole.getRoleName() + " is already exist", exception.getMessage());
        verify(roleRepository, times(1)).findByRoleName(existingRole.getRoleName());
        verify(roleRepository, times(0)).save(existingRole);
    }
    @Test
    public void testGetRoleByName_Success() {
        String roleName = "USER";
        Role role = new Role(1L, roleName);
        given(roleRepository.findByRoleName(roleName)).willReturn(Optional.of(role));

        Role result = roleService.getByName(roleName);

        assertNotNull(result);
        assertEquals(roleName, result.getRoleName());
        assertEquals(role.getId(), result.getId());
    }
    @Test
    public void testGetRoleByName_RoleNotFound() {
        String roleName = "ADMIN";
        given(roleRepository.findByRoleName(roleName)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            roleService.getByName(roleName);
        });
    }
}
