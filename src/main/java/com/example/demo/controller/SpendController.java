package com.example.demo.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.service.SpendService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SpendController {
    @Autowired
    SpendService spendservice;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/spenddetail/page", method = RequestMethod.GET)
    public String findPage(@RequestParam("pageNum") int i,
                           @RequestParam("pageSize") int j,
                           @RequestParam(value = "username", defaultValue = "", required = false) String username,
                           @RequestParam(value = "kind", defaultValue = "", required = false) String kind,
                           @RequestParam(value = "fromway",required = false) String fromway,
                           @RequestParam(value = "begintime",required = false) String begint,
                           @RequestParam(value = "endtime",required = false) String endt) {
        DateTime begin = DateUtil.parse(begint);
        DateTime end = DateUtil.parse(endt);
        int total = spendservice.count(username,kind,fromway,begin,end);
        Object onePage = spendservice.selectByPage(i, j, username, kind,fromway,begin,end);
        Map<String, Object> result = new HashMap<>();
        result.put("count", total);
        result.put("data", onePage);
        return Result.okGetStringByData("ok", result);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "spenddetail/save", method = RequestMethod.POST)
    public String save(@RequestBody Map<String,String> map) {
        int id;
        if(StrUtil.isBlank(map.get("id"))) {
            id = -1;
        }
        else {
            id = Integer.parseInt(map.get("id"));
        }
        double value = Double.parseDouble(map.get("value"));
        String describe = map.get("description");
        String kind = map.get("kind");
        String time = map.get("time");
        Date t = DateUtil.parse(time);
        String fromway = map.get("fromway");
        String username = map.get("username");
        spendservice.save(id,value,describe,kind,username,t,fromway);
        return Result.okGetString("ok");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/spenddetail/del", method = RequestMethod.DELETE)
    public String delUser(@RequestParam("id") Integer id,
                          @RequestParam(value = "username",required = false) String username,
                          @RequestParam(value = "fromway", required = false) String fromway) {

        spendservice.delUser(id,username,fromway);
        return Result.okGetString("ok");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/spend/today", method = RequestMethod.GET)
    public String todayspend(@RequestParam("username") String username,
                             @RequestParam("date") String date) {
        DateTime time = DateUtil.parse(date);
        return Result.okGetStringByData("ok",spendservice.counttodayspend(username,time));
    }
//    @CrossOrigin(origins = "*")
//    @RequestMapping(value = "/spenddetail/delbatch", method = RequestMethod.DELETE)
//    public String delUser(@RequestBody Integer [] ids) {
//        ArrayList<Integer> res = new ArrayList<>(Arrays.asList(ids));
//        spendservice.delUserBatch(res);
//        return Result.okGetString("ok");
//    }
}
