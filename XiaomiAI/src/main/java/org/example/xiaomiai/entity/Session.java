package org.example.xiaomiai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class Session {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String sessionName;
    private Long createTime;
    private Long updateTime;

    public Session(){
        this.id = null;
        this.userId = null;
        this.sessionName = null;
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
    }
}
