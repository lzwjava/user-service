package com.demo.lab.role;

import com.demo.lab.base.Repository;

public class RoleRepository extends Repository<Role> {

    private static RoleRepository roleRepository;

    public static RoleRepository getInstance() {
        if (roleRepository == null) {
            roleRepository = new RoleRepository();
        }
        return roleRepository;
    }

    public Role getRoleByName(String name) {
        return getEntity(role -> role.getName().equals(name));
    }

    public boolean saveRole(Role role) {
        return saveEntity(role);
    }

    public boolean deleteRole(int id) {
        return deleteEntity(id);
    }

}
