package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.User;

public interface UserService {

    User loadUserByUsername(String username);

    int addUser(User user);

    int update(User user);
}
