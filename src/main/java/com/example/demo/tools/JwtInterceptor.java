package com.example.demo.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userservice;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        if(StrUtil.isBlank(token)) {
            throw new AppException(AppExceptionCodeMsg.NO_TOKEN);
        }
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new AppException(AppExceptionCodeMsg.ERROR_TOKEN);
        }
        if(!userservice.usernameexist(userId)) {
            throw new AppException(AppExceptionCodeMsg.ERROR_TOKEN);
        }
        UserPojo pojo = (UserPojo)userservice.getuserinfo(userId);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(pojo.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AppException(AppExceptionCodeMsg.ERROR_TOKEN);
        }
        return true;
    }
}
