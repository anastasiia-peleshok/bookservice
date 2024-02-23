package com.nata.bookspace.bookservice.service;

import com.nata.bookspace.bookservice.entity.Role;

public interface RoleService {
    Role save(Role role);

    Role getByName(String roleName);
}
