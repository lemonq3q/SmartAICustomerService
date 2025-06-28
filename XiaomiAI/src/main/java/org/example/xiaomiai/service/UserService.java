package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    int addUser(User user);

    int update(User user);

    User getUserByPhone(String phone);
}
