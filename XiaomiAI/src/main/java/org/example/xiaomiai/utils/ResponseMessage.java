package org.example.xiaomiai.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {
    private int code;
    private String message;
    private Object data;

    public static ResponseMessage success(String message, Object data){
        return new ResponseMessage(200, message, data);
    }

    public static ResponseMessage failed(String message){
        return new ResponseMessage(400, message, null);
    }

    public static ResponseMessage failed(int code, String message){
        return new ResponseMessage(code, message, null);
    }
}
