package org.example.xiaomiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.KnowledgeStore;
import org.example.xiaomiai.mapper.KnowledgeStoreMapper;
import org.example.xiaomiai.service.KnowledgeService;
import org.example.xiaomiai.service.KnowledgeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeStoreServiceImpl implements KnowledgeStoreService {

    @Resource
    private KnowledgeStoreMapper knowledgeStoreMapper;

    @Autowired
    private KnowledgeService knowledgeService;

    @Override
    @Cacheable(value = "knowledgeStoreCache", key = "#userId")
    public List<KnowledgeStore> selectKnowledgeByUserId(int userId) {
        QueryWrapper<KnowledgeStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<KnowledgeStore> res = knowledgeStoreMapper.selectList(queryWrapper);
        return res;
    }

    @Override
    @CacheEvict(value = "knowledgeStoreCache", key = "#knowledgeStore.userId")
    public KnowledgeStore addKnowledge(KnowledgeStore knowledgeStore) {
        knowledgeStore.setId(null);
        knowledgeStoreMapper.insert(knowledgeStore);
        return knowledgeStore;
    }

    @Override
    @CacheEvict(value = "knowledgeStoreCache", key = "#knowledgeStore.userId")
    public int deleteKnowledge(KnowledgeStore knowledgeStore) {
        if(knowledgeStore.getId() == null){
            return 0;
        }
        int x = knowledgeStoreMapper.deleteById(knowledgeStore);
        if(x != 0){
            knowledgeService.deleteKnowledgeByStore(knowledgeStore.getStoreName());
        }
        return x;
    }
}
