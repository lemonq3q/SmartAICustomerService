package org.example.xiaomiai.interceptor;

import org.example.xiaomiai.utils.ResponseMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMessage exceptionHandler(Exception e){
        e.printStackTrace();
        return ResponseMessage.failed(e.getMessage());
    }
}
