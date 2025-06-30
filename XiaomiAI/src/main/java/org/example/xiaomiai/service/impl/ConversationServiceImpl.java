package org.example.xiaomiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.xiaomiai.entity.Conversation;
import org.example.xiaomiai.entity.Session;
import org.example.xiaomiai.mapper.ConversationMapper;
import org.example.xiaomiai.service.ConversationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Resource
    private ConversationMapper conversationMapper;

    @Override
    @Cacheable(value = "conversationCache", key = "#sessionId")
    public List<Conversation> selectConversationBySessionId(int sessionId) {
        QueryWrapper<Conversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("session_id", sessionId);
        List<Conversation> res = conversationMapper.selectList(queryWrapper);
        return res;
    }

    @Override
    @CacheEvict(value = "conversationCache", key = "#conversation.sessionId")
    public int addConversation(Conversation conversation) {
        conversation.setId(null);
        int x = conversationMapper.insert(conversation);
        return x;
    }

    @Override
    @CacheEvict(value = "conversationCache", key = "#sessionId")
    public int deleteConversationBySessionId(int sessionId) {
        QueryWrapper<Conversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("session_id", sessionId);
        int x = conversationMapper.delete(queryWrapper);
        return x;
    }

    @Override
    public List<Conversation> selectConversationByCondition(Conversation params, long start, long end) {
        QueryWrapper<Conversation> queryWrapper = new QueryWrapper<>();
        if (params.getSessionId() != null) {
            queryWrapper.eq("session_id", params.getSessionId());
        }
        if (params.getUserId() != null) {
            queryWrapper.eq("user_id", params.getUserId());
        }
        if (params.getRole() != null) {
            queryWrapper.eq("role", params.getRole());
        }

        if (params.getContent() != null) {
            queryWrapper.like("content", params.getContent()); // 使用模糊匹配
        }
        queryWrapper.between("time", start, end);

        return conversationMapper.selectList(queryWrapper);
    }
}
