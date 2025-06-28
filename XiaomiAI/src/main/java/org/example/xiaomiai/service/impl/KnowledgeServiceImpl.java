package org.example.xiaomiai.service.impl;

import jakarta.annotation.Resource;
import org.example.xiaomiai.mapper.KnowledgeMapper;
import org.example.xiaomiai.service.KnowledgeService;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Resource
    private KnowledgeMapper knowledgeMapper;
}
