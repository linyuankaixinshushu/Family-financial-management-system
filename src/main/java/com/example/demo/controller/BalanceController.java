package com.example.demo.controller;


import com.example.demo.pojo.BalancePojo;
import com.example.demo.service.BalanceService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BalanceController {
    @Autowired
    BalanceService balanceservice;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/balance/page", method = RequestMethod.GET)
    public String findPage(@RequestParam("pageNum") int pageNum,
                           @RequestParam("pageSize") int pageSize){
        int total = balanceservice.count();
        Object onePage = balanceservice.selectByPage(pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("count", total);
        result.put("data", onePage);
        return Result.okGetStringByData("ok", result);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/balance/add", method = RequestMethod.POST)
    public String addfrom(@RequestBody Map<String,String> map) {
        double value = Double.parseDouble(map.get("value"));
        balanceservice.addfromway(map.get("username"),map.get("fromway"),value);
        return Result.okGetString("ok");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/balance/del", method = RequestMethod.DELETE)
    public String delUser(@RequestParam("username") String username,
                          @RequestParam("fromway") String fromway) {
        balanceservice.delfromway(username,fromway);
        return Result.okGetString("ok");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/balance/userwayshas", method = RequestMethod.GET)
    public String delUser(@RequestParam("username") String username) {
        return Result.okGetStringByData("ok",balanceservice.finduserhasway(username));
    }
}
