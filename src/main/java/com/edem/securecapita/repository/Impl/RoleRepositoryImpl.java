package com.edem.securecapita.repository.Impl;

import com.edem.securecapita.model.Role;
import com.edem.securecapita.repository.RoleRepository;

import java.util.Collection;

public class RoleRepositoryImpl implements RoleRepository<Role> {
    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {

    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateRole(Long userId, String roleName) {

    }
}
