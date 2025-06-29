package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.Session;
import org.example.xiaomiai.service.SessionService;
import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseMessage selectSession(int userId){
        List<Session> res = sessionService.selectSessionByUser(userId);
        return ResponseMessage.success("搜索成功",res);
    }

    @PostMapping
    public ResponseMessage createSession(@RequestBody Session session){
        Session res = sessionService.createSession(session);
        return ResponseMessage.success("会话创建成功", session);
    }

    @DeleteMapping
    public ResponseMessage deleteSession(Session session){
        int x = sessionService.deleteSession(session);
        if(x == 0){
            return ResponseMessage.failed("sessionId不存在");
        }
        else {
            return ResponseMessage.success("删除成功");
        }
    }
}
