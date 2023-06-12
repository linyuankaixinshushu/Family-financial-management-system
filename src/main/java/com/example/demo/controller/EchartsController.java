package com.example.demo.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.demo.service.IncomeService;
import com.example.demo.service.SpendService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EchartsController {
    @Autowired
    IncomeService incomeService;
    @Autowired
    SpendService spendService;


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/incomeandspend", method = RequestMethod.GET)
    public String getincomeandspenddata(@RequestParam(value = "begintime") String begin,
                                        @RequestParam(value = "username",required = false) String username){
        Map<String,Object> res = new HashMap<>();
        DateTime weekbegin = DateUtil.parse(begin);
        weekbegin = DateUtil.beginOfWeek(weekbegin);
        ArrayList<Double> weekincome = incomeService.getweekincome(weekbegin,username);
        ArrayList<Double> weekspend = spendService.getweekspend(weekbegin,username);
        ArrayList<Double> weeksurplus = new ArrayList<>();
        for(int i = 0; i < weekincome.size(); i++) {
            weeksurplus.add(weekincome.get(i) - weekspend.get(i));
        }
        res.put("weekincome",weekincome);
        res.put("weekspend",weekspend);
        res.put("weeksurplus",weeksurplus);
        return Result.okGetStringByData("ok",res);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/income", method = RequestMethod.GET)
    public String getoption2(@RequestParam(value = "begintime", required = false) String begin,
                             @RequestParam(value = "endtime", required = false) String end,
                             @RequestParam(value = "username",required = false) String username) {
        DateTime tbegin = DateUtil.parse(begin);
        DateTime tend = DateUtil.parse(end);
        return Result.okGetStringByData("ok",incomeService.getoption2(username,tbegin,tend));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/spend", method = RequestMethod.GET)
    public String getoption3(@RequestParam(value = "begintime", required = false) String begin,
                             @RequestParam(value = "endtime", required = false) String end,
                             @RequestParam(value = "username",required = false) String username) {
        DateTime tbegin = DateUtil.parse(begin);
        DateTime tend = DateUtil.parse(end);
        return Result.okGetStringByData("ok",spendService.getoption3(username,tbegin,tend));
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/incomebyuser", method = RequestMethod.GET)
    public String getincomebyuseroption(@RequestParam(value = "begintime", required = false) String begin,
                                        @RequestParam(value = "endtime", required = false) String end) {
        DateTime tbegin = DateUtil.parse(begin);
        DateTime tend = DateUtil.parse(end);
        return Result.okGetStringByData("ok",incomeService.getincomebyuseroption(tbegin,tend));
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/spendbyuser", method = RequestMethod.GET)
    public String getspendbyuseroption(@RequestParam(value = "begintime", required = false) String begin,
                                       @RequestParam(value = "endtime", required = false) String end) {
        DateTime tbegin = DateUtil.parse(begin);
        DateTime tend = DateUtil.parse(end);
        return Result.okGetStringByData("ok",spendService.getspendbyuseroption(tbegin,tend));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/getfamilyincomeop", method = RequestMethod.GET)
    public String getoption4(@RequestParam(value = "time") String time,
                             @RequestParam(value = "flag") String flag,
//                             @RequestParam(value = "username",required = false) String username,
//                             @RequestParam(value = "kind",required = false) String kind,
                             @RequestParam(value = "len") int len) {
        DateTime t = DateUtil.parse(time);
        return  Result.okGetStringByData("ok",incomeService.getoption4(t,flag,len));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/echarts/getfamilyspendop", method = RequestMethod.GET)
    public String getoption5(@RequestParam(value = "time") String time,
                             @RequestParam(value = "flag") String flag,
//                             @RequestParam(value = "username",required = false) String username,
//                             @RequestParam(value = "kind",required = false) String kind,
                             @RequestParam(value = "len") int len) {
        DateTime t = DateUtil.parse(time);
        return  Result.okGetStringByData("ok",spendService.getoption5(t,flag,len));
    }
}
