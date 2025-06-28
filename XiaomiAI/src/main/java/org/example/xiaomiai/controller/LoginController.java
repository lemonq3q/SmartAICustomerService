package org.example.xiaomiai.controller;

import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/test")
    public ResponseMessage testLogin(){
        return ResponseMessage.success("验证成功，用户已登录", null);
    }
}
