package org.example.xiaomiai.service.impl;

import jakarta.annotation.Resource;
import org.example.xiaomiai.mapper.ConversationMapper;
import org.example.xiaomiai.service.ConversationService;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Resource
    private ConversationMapper conversationMapper;
}
