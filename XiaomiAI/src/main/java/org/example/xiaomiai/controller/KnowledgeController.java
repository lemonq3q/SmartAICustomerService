package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.KnowledgeStore;
import org.example.xiaomiai.service.KnowledgeService;
import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping
    public ResponseMessage selectKnowledge(KnowledgeStore knowledgeStore){
        List<Knowledge> res = knowledgeService.selectKnowledgeByStore(knowledgeStore);
        return ResponseMessage.success("查询成功", res);
    }

    @PostMapping
    public ResponseMessage insertKnowledge(String content, @RequestBody KnowledgeStore knowledgeStore){
        knowledgeService.addKnowledge(knowledgeStore, content);
        return ResponseMessage.success("添加成功");
    }

    @DeleteMapping
    public ResponseMessage deleteKnowledge(Knowledge knowledge){
        int x = knowledgeService.deleteKnowledge(knowledge);
        if(x == 0){
            return ResponseMessage.failed("知识不存在");
        }
        else{
            return ResponseMessage.success("删除成功");
        }
    }
}
