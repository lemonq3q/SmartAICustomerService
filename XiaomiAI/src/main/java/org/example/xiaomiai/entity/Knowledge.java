package org.example.xiaomiai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Knowledge {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer storeId;
    private String storeName;
    private String content;
    private Long createTime;

    @TableField(exist = false)
    public static String SESSION_PREFIX = "/session_id:";
}
