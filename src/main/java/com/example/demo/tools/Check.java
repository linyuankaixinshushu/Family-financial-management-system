package com.example.demo.tools;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import org.springframework.stereotype.Component;

public class Check {
    public static boolean check_username(String username) {
        return !StrUtil.isBlank(username) && ReUtil.isMatch("^[A-Za-z0-9\\u4e00-\\u9fa5_.]+$", username);
    }

    public static boolean check_password(String password) {
        return !StrUtil.isBlank(password) && password.length() >= 6 && ReUtil.isMatch("^[A-Za-z0-9_.]+$", password);
    }

    public static boolean check_null(String a) {
        return StrUtil.isEmpty(a);
    }
}
