package org.example.xiaomiai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KnowledgeStore {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String storeName;
    private Integer userId;
    private Integer total;
    private Integer type;   // 0为用户上传知识库，1为用户对话记录知识库
}
