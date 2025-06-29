package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.KnowledgeStore;
import org.example.xiaomiai.service.KnowledgeStoreService;
import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class KnowledgeStoreController {
    @Autowired
    private KnowledgeStoreService knowledgeStoreService;

    @GetMapping
    public ResponseMessage selectStore(int userId){
        List<KnowledgeStore> res = knowledgeStoreService.selectKnowledgeByUserId(userId);
        return ResponseMessage.success("查询成功", res);
    }

    @PostMapping
    public ResponseMessage addStore(@RequestBody KnowledgeStore knowledgeStore){
        KnowledgeStore res = knowledgeStoreService.addKnowledge(knowledgeStore);
        return ResponseMessage.success("创建成功", res);
    }

    @DeleteMapping
    public ResponseMessage deleteStore(KnowledgeStore knowledgeStore){
        int x = knowledgeStoreService.deleteKnowledge(knowledgeStore);
        if(x == 0){
            return ResponseMessage.failed("知识库不存在");
        }
        else{
            return ResponseMessage.success("删除成功");
        }
    }
}
