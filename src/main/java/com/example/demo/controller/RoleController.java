package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.service.RoleService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RoleController {
    @Autowired
    RoleService roleservice;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/role/page", method = RequestMethod.GET)
    public String findPage(@RequestParam("pageNum") int i,
                           @RequestParam("pageSize") int j,
                           @RequestParam("currole") String currole){
        int total = roleservice.count(currole);
        Object onePage = roleservice.selectByPage(i, j,currole);
        Map<String, Object> result = new HashMap<>();
        result.put("count", total);
        result.put("data", onePage);
        return Result.okGetStringByData("ok", result);
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/role/save", method = RequestMethod.POST)
    public String save(@RequestBody Map<String,String> map) {
        int id;
        if(StrUtil.isBlank(map.get("id"))) {
            id = -1;
        }
        else {
            id = Integer.parseInt(map.get("id"));
        }
        String name = map.get("name");
        String describe = map.get("describe");
        roleservice.update(id,name,describe);
        return Result.okGetString("ok");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/role/del", method = RequestMethod.DELETE)
    public String delUser(@RequestParam("id") Integer id) {
        roleservice.delUser(id);
        return Result.okGetString("ok");
    }
}


