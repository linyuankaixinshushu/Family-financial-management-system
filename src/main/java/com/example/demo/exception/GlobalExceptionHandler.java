package com.example.demo.exception;

import com.example.demo.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.rmi.CORBA.Util;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public String exceptionHandler(Exception e) {
        if(e instanceof AppException) {
            AppException appException = (AppException)e;
            return Result.errorGetString(appException.getCode(),appException.getMsg());
        }

        return Result.errorGetString(500,"服务器异常");
    }
}
