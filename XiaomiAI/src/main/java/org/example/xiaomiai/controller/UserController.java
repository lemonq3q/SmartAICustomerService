package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.User;
import org.example.xiaomiai.service.UserService;
import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseMessage registered(@RequestBody User user){
        int res = userService.addUser(user);
        if(res == -1){
            return ResponseMessage.failed("用户名重复");
        }
        else {
            return ResponseMessage.success("用户注册成功", null);
        }
    }

    @PutMapping
    public ResponseMessage updateUser(@RequestBody User user){
        int res = userService.update(user);
        if(res == -1){
            return ResponseMessage.failed("用户名重复");
        }
        else if(res == 0){
            return ResponseMessage.failed("无更新结果");
        }
        else{
            return ResponseMessage.success("用户更新成功", null);
        }
    }
}
