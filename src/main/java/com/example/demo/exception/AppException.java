package com.example.demo.exception;

public class AppException extends RuntimeException{
    private int code = 500;
    private String msg = "服务器错误";

    public AppException(AppExceptionCodeMsg appExceptionCodeMsg)
    {
        super();
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();
    }

    public AppException(int code,String msg){
        super();
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
