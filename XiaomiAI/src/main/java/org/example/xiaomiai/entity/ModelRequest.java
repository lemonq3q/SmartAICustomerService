package org.example.xiaomiai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelRequest {
    private Integer userId;
    private String requestContent;
    private String modelName;
    private String storeName;
    private Integer sessionId;
}
