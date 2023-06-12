package com.example.demo.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Result {

    public static String okGetString(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code" , 200);
        map.put("message", message);
        return JSONObject.toJSONString(map);
    }
    public static String okGetStringByData(String message, Object content) {
        Map<String, Object> map = new HashMap<>();
        map.put("code" , 200);
        map.put("message", message);
        map.put("data", content);
        System.out.println(map);
        return JSONObject.toJSONString(map);
    }

    public static String errorGetString(int code,String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code" , code);
        map.put("message", msg);
        return JSONObject.toJSONString(map);
    }

    public static String okGetStringByDataAndToken(String message, Object content) {
        Map<String, Object> map = new HashMap<>();
        map.put("code" , 200);
        map.put("message", message);
        map.put("data", content);
        System.out.println(map);
        return JSONObject.toJSONString(map);
    }
}
