package org.example.xiaomiai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer sessionId;
    private Integer userId;
    private Integer role;  // 0为ai，1为用户
    private String content;
    private Long time;
}
