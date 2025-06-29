package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.Model;
import org.example.xiaomiai.service.ModelService;
import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping
    public ResponseMessage queryAllModel(){
        List<Model> res = modelService.selectAllModel();
        return ResponseMessage.success("查询成功", res);
    }

    @PostMapping
    public ResponseMessage addModelService(@RequestBody Model model){
        Model res = modelService.addModel(model);
        return ResponseMessage.success("添加成功");
    }

    @DeleteMapping
    public ResponseMessage deleteModel(int id){
        int x = modelService.deleteModelById(id);
        if(x == 0){
            return ResponseMessage.failed("id不存在");
        }
        else{
            return ResponseMessage.success("删除成功");
        }
    }
}
