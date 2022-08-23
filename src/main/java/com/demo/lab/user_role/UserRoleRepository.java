package com.demo.lab.user_role;


import com.demo.lab.base.Repository;

import java.util.List;
import java.util.stream.Collectors;

class UserRoleRepository extends Repository<UserRole> {

    private static UserRoleRepository userRoleRepository;

    public static UserRoleRepository getInstance() {
        if (userRoleRepository == null) {
            userRoleRepository = new UserRoleRepository();
        }
        return userRoleRepository;
    }

    public boolean saveUserRole(UserRole userRole) {
        return saveEntity(userRole);
    }

    public UserRole getUserRole(UserRole targetUserRole) {
        return getUserRole(targetUserRole.getUserId(), targetUserRole.getRoleId());
    }

    public UserRole getUserRole(int targetUserId, int targetRoleId) {
        return getEntity(userRole -> userRole.getUserId() == targetUserId && userRole.getRoleId() == targetRoleId);
    }

    public List<UserRole> getUserRoles(int targetUserId) {
        return entities.stream().filter(userRole -> userRole.getUserId() == targetUserId).collect(Collectors.toList());
    }

}
