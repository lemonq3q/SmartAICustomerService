package org.example.xiaomiai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Model {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String modelName;
    private String host;
    private Integer port;

    public Model(){
        this.id = null;
        this.modelName = null;
        this.host = "localhost";
        this.port = 11434;
    }
}
