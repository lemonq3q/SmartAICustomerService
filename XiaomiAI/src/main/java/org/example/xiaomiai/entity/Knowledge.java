package org.example.xiaomiai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Knowledge {
    private Integer id;
    private String collectionName;
    private Integer userId;
    private Integer total;
    private Integer type;   // 0为用户上传知识库，1为用户对话记录知识库
}
