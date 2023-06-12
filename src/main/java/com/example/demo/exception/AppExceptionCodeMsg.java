package com.example.demo.exception;


public enum AppExceptionCodeMsg {
    USER_NOT_EXISTS(10000,"用户名不存在"),
    PASSWORD_ERROR(10001, "密码错误"),
    USER_EXISTS(10002,"用户名已经存在"),
    ERROR_USERNAME(10003,"用户名为空或存在非法字符"),
    NO_TOKEN(10004, "请重新登录！"),
    ERROR_TOKEN(10004,"token验证失败"),
    NO_ROLENAME(10005,"请输入角色名"),
    DEFAULT_ROLE(10006,"请勿删除默认用户！"),
    EXIST_FROM(10007,"已经存在的支付方式"),
    DELROLEFAILED(10008,"请确认没有用户拥有该角色");

    final private int code;
    final private String msg;

    AppExceptionCodeMsg(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return this.code;
    }

    public String getMsg()
    {
        return this.msg;
    }

}
