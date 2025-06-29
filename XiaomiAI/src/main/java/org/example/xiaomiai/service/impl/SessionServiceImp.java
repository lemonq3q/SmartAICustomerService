package org.example.xiaomiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.Session;
import org.example.xiaomiai.mapper.SessionMapper;
import org.example.xiaomiai.service.ConversationService;
import org.example.xiaomiai.service.KnowledgeService;
import org.example.xiaomiai.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImp implements SessionService {

    @Resource
    private SessionMapper sessionMapper;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Override
    @CacheEvict(value = "sessionCache", key = "#session.userId")
    public Session createSession(Session session) {
        session.setId(null);
        sessionMapper.insert(session);
        return session;
    }

    @Override
    @Cacheable(value = "sessionCache", key = "#userId")
    public List<Session> selectSessionByUser(int userId) {
        QueryWrapper<Session> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Session> res = sessionMapper.selectList(queryWrapper);
        return res;
    }

    @Override
    @CacheEvict(value = "sessionCache", key = "#session.userId")
    public int deleteSession(Session session) {
        if(session.getId() == null){
            return 0;
        }
        int x = sessionMapper.deleteById(session);
        System.out.println(x);
        if(x != 0){
            conversationService.deleteConversationBySessionId(session.getId());
            knowledgeService.deleteKnowledgeByStore(Knowledge.SESSION_PREFIX +session.getId());
        }
        return x;
    }


}
