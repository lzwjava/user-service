package com.demo.lab.user;

import com.demo.lab.utils.TokenUtils;
import com.demo.lab.base.Repository;

public class UserRepository extends Repository<User> {

    private static UserRepository userRepository;

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public User getUserByUsername(String username) {
        return getEntity(user -> user.getUsername().equals(username));
    }

    public User getUserById(int id) {
        return getEntityById(id);
    }

    public boolean saveUser(User user) {
        user.setPassword(TokenUtils.encryptPassword(user.getPassword()));
        return saveEntity(user);
    }

    public boolean deleteUser(int id) {
        return deleteEntity(id);
    }

    public User getUser(User targetUser) {
        String encrypted = TokenUtils.encryptPassword(targetUser.getPassword());
        return getEntity(user -> user.getUsername().equals(targetUser.getUsername()) &&
            user.getPassword().equals(encrypted));
    }

    public User getUserByToken(String token) {
        return getEntity(user -> user.getToken() != null && user.getToken().equals(token));
    }
}
