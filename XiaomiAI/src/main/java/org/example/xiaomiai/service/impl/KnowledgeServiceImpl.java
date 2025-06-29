package org.example.xiaomiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.KnowledgeStore;
import org.example.xiaomiai.mapper.KnowledgeMapper;
import org.example.xiaomiai.service.KnowledgeService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private VectorStore vectorStore;

    private final static Integer TOP_K = 5;
    private final static Double SIMILARITY_THRESHOLD = 0.5;


    @Override
    @Cacheable(value = "knowledgeCache", key = "#knowledgeStore.storeName")
    public List<Knowledge> selectKnowledgeByStore(KnowledgeStore knowledgeStore) {
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_name", knowledgeStore.getStoreName());
        List<Knowledge> res = knowledgeMapper.selectList(queryWrapper);
        return res;
    }

    @Override
    public String getRagData(String request, String storeName) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.eq("store_name", storeName).build();
        SearchRequest searchRequest = SearchRequest.builder()
                .query(request)
                .topK(TOP_K)
                .similarityThreshold(SIMILARITY_THRESHOLD)
                .filterExpression(expression)
                .build();
        List<Document> docs = vectorStore.similaritySearch(searchRequest);
        StringBuffer stringBuffer = new StringBuffer();
        for(Document doc : docs){
            stringBuffer.append(doc.getText());
            stringBuffer.append("\n");
        }
        return new String(stringBuffer);
    }

    @Override
    @CacheEvict(value = "knowledgeCache", key = "#knowledgeStore.storeName")
    public Knowledge addKnowledge(KnowledgeStore knowledgeStore, String content) {

        Knowledge knowledge = new Knowledge(null, knowledgeStore.getId(), knowledgeStore.getStoreName(), content, System.currentTimeMillis());
        knowledgeMapper.insert(knowledge);
        Document doc = new Document(
            content,
            Map.of(
                "store_name", knowledgeStore.getStoreName(),
                "id",knowledge.getId()
            )
        );
        vectorStore.add(List.of(doc));
        return knowledge;
    }

    @Override
    @CacheEvict(value = "knowledgeCache", key = "#knowledge.storeName")
    public int deleteKnowledge(Knowledge knowledge) {
        if(knowledge.getId() == null){
            return 0;
        }
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.eq("id", knowledge.getId()).build();
        vectorStore.delete(expression);
        int x = knowledgeMapper.deleteById(knowledge);
        return x;
    }

    @Override
    @CacheEvict(value = "knowledgeCache", key = "#storeName")
    public int deleteKnowledgeByStore(String storeName) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.eq("store_name", storeName).build();
        vectorStore.delete(expression);
        return 1;
    }

    @Override
    public int addSessionKnowledge(String storeName, String content){
        Document doc = new Document(
            content,
            Map.of(
                "store_name", storeName
            )
        );
        vectorStore.add(List.of(doc));
        return 1;
    }
}
