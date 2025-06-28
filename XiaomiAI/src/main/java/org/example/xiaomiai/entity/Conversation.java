package org.example.xiaomiai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Conversation {
    private Integer id;
    private Integer sessionId;
    private Integer userId;
    private Integer role;  // 0为ai，1为用户
    private String content;
    private LocalDateTime time;
}
