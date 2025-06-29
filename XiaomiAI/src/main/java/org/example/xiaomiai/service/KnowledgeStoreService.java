package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.KnowledgeStore;

import java.util.List;

public interface KnowledgeStoreService {

    List<KnowledgeStore> selectKnowledgeByUserId(int userId);

    KnowledgeStore addKnowledge(KnowledgeStore knowledgeStore);

    int deleteKnowledge(KnowledgeStore knowledgeStore);
}
