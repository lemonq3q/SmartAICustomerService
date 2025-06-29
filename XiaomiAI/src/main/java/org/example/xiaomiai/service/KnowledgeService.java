package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.KnowledgeStore;

import java.util.List;

public interface KnowledgeService {
    List<Knowledge> selectKnowledgeByStore(KnowledgeStore knowledgeStore);

    String getRagData(String request, String storeName);

    Knowledge addKnowledge(KnowledgeStore knowledgeStore, String content);

    int deleteKnowledge(Knowledge knowledge);

    int deleteKnowledgeByStore(String storeName);

    public int addSessionKnowledge(String storeName, String content);
}
