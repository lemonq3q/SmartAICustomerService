package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.Conversation;
import org.example.xiaomiai.service.ConversationService;
import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping
    public ResponseMessage selectBySessionId(int sessionId){
        List<Conversation> res = conversationService.selectConversationBySessionId(sessionId);
        return ResponseMessage.success("查询成功", res);
    }
}
