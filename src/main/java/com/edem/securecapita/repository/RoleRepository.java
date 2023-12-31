package com.edem.securecapita.repository;

import com.edem.securecapita.model.Role;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    T create(T data);
    Collection<T> list (int page, int pageSize);

    T get(Long id);
    T update (T data);
    Boolean delete(Long id);

    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateRole(Long userId, String roleName);
}
