package org.example.xiaomiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.xiaomiai.entity.User;
import org.example.xiaomiai.mapper.UserMapper;
import org.example.xiaomiai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            int x = userMapper.insert(user);
            return x;
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(User user) {
        if(user.getId() == null){
            return 0;
        }
        try {
            int x = userMapper.updateById(user);
            return x;
        } catch (DuplicateKeyException e){
            e.printStackTrace();
            return -1;
        }
    }
}
