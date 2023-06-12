package com.example.demo.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.SpendDao;
import com.example.demo.dao.UserDao;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.pojo.BalancePojo;
import com.example.demo.pojo.IncomePojo;
import com.example.demo.pojo.SpendPojo;
import com.example.demo.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SpendService  {
    @Autowired
    SpendDao spenddao;
    @Autowired
    BalanceDao balancedao;
    @Autowired
    UserDao userdao;
    public int count(String username,String kind,String fromway,Date begin,Date end)
    {
        return spenddao.count(username,kind,fromway,begin,end);
    }

    public Object selectByPage(int i, int j, String username, String kind,String fromway,Date begin,Date end) {
        int ItemNumber = (i - 1) * j;
        return spenddao.findByPage(ItemNumber,j,username,kind,fromway,begin,end);
    }

    public void save(int id, double value, String describe, String kind, String username, Date t,String fromway) {
        QueryWrapper<BalancePojo> qw = new QueryWrapper<>();
        qw.eq("username",username);
        qw.eq("fromway",fromway);
        double v = balancedao.selectOne(qw).getValue();
        SpendPojo pojo = new SpendPojo();
        pojo.setTime(t);
        pojo.setDescription(describe);
        pojo.setKind(kind);
        pojo.setValue(value);
        pojo.setUsername(username);
        pojo.setFromway(fromway);
        if(spenddao.exists(new QueryWrapper<SpendPojo>().eq("id",id))){
            double raw_v = spenddao.selectById(id).getValue();
            v += raw_v;
            pojo.setId(id);
            spenddao.updateById(pojo);
        }
        else {
            spenddao.insert(pojo);
        }
        v -= value;
        balancedao.update(new BalancePojo(username,fromway,v),qw);
    }

    public void delUser(Integer id,String username,String fromway) {
        if(username != null && fromway != null)
        {
            QueryWrapper<BalancePojo> qw = new QueryWrapper<>();
            qw.eq("username",username);
            qw.eq("fromway",fromway);
            double value = balancedao.selectOne(qw).getValue();
            double v = spenddao.selectById(id).getValue();
            value += v;
            balancedao.update(new BalancePojo(username,fromway,value),qw);
        }
        spenddao.deleteById(id);
    }

    public ArrayList<Double> getweekspend(DateTime weekbegin, String username) {
        ArrayList<Double> res = new ArrayList<>();
        DateTime tmp = weekbegin;
        for(int i = 1; i <= 7; i++) {
            QueryWrapper<SpendPojo> qw = new QueryWrapper<>();
//            qw.ge("time",tmp);
//            qw.lt("time", DateUtil.offsetDay(weekbegin,i));
            qw.eq("username",username);
            qw.between("time",tmp, DateUtil.offsetDay(weekbegin,i));
            ArrayList<SpendPojo> pojos = (ArrayList<SpendPojo>)spenddao.selectList(qw);
            double count = 0;
            for(SpendPojo item : pojos){
                count += item.getValue();
            }
            res.add(count);
            tmp = DateUtil.offsetDay(weekbegin,i);
        }
        return res;
    }
    public Double counttodayspend(String username,DateTime time) {
        QueryWrapper<SpendPojo> qw = new QueryWrapper<>();
        qw.eq("username",username);
        qw.between("time",time,DateUtil.offsetDay(time,1));
        ArrayList<SpendPojo> pojos = (ArrayList<SpendPojo>)spenddao.selectList(qw);
        double count = 0;
        for(SpendPojo pojo : pojos){
            count += pojo.getValue();
        }
        return count;
    }

    public ArrayList<Map<String,Object>> getoption3(String username, DateTime tbegin, DateTime tend) {
        ArrayList<Map<String,Object>> res = new ArrayList<>();
        ArrayList<String> spendkind = new ArrayList<>();
        spendkind.add("生活");
        spendkind.add("投资");
        spendkind.add("食物");
        spendkind.add("交通");
        spendkind.add("教育");
        spendkind.add("其他");
        spendkind.add("娱乐");
        spendkind.add("医疗");
        for(String i:spendkind) {
            QueryWrapper<SpendPojo> qw = new QueryWrapper<>();
            if(!StrUtil.isBlank(username)) {
                qw.eq("username", username);
            }
            if(tbegin != null) {
                qw.between("time",tbegin,tend);
            }
            qw.eq("kind",i);
            ArrayList<SpendPojo> pojos = (ArrayList<SpendPojo>)spenddao.selectList(qw);
            double count = 0;
            for(SpendPojo pojo: pojos){
                count += pojo.getValue();
            }
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("name",i);
            tmp.put("value",count);
            res.add(tmp);
        }
        return res;
    }

    public Object getspendbyuseroption(DateTime tbegin, DateTime tend) {
        ArrayList<Map<String,Object>> res = new ArrayList<>();
        ArrayList<UserPojo> upojos = (ArrayList<UserPojo>)userdao.selectList(new QueryWrapper<>());
        for(UserPojo upojo : upojos){
            QueryWrapper<SpendPojo> qw = new QueryWrapper<>();
            qw.eq("username",upojo.getUsername());
            if(tbegin != null) {
                qw.between("time",tbegin,tend);
            }
            double count = 0;
            ArrayList<SpendPojo> spojos = (ArrayList<SpendPojo>)spenddao.selectList(qw);
            for(SpendPojo spojo : spojos) {
                count += spojo.getValue();
            }
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("name",upojo.getUsername());
            tmp.put("value",count);
            res.add(tmp);
        }
        return res;
    }

    public Object getoption5(DateTime t, String flag, int len) {
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Double> data = new ArrayList<>();
        Map<String,Object> res = new HashMap<>();
        if(flag.equals("month")) {
            t = DateUtil.beginOfMonth(t);
        }
        else if(flag.equals("year")){
            t = DateUtil.beginOfYear(t);
        }
        int tmp_len = len/2;
        for(int i = 0; i < tmp_len; i++) {
            DateTime tmp;
            QueryWrapper<SpendPojo> qw = new QueryWrapper<>();
            if(flag.equals("month")) {
                tmp = DateUtil.offsetMonth(t,i - tmp_len);
                x.add(DateUtil.format(tmp,"yyyy/MM"));
                qw.between("time",tmp,DateUtil.offsetMonth(tmp,1));
            }
            else if(flag.equals("year")){
                tmp = DateUtil.offset(t, DateField.YEAR,i - tmp_len);
                x.add(DateUtil.format(tmp,"yyyy"));
                qw.between("time",tmp,DateUtil.offset(tmp,DateField.YEAR,1));
            }
            else{
                tmp = DateUtil.offsetDay(t,i - tmp_len);
                x.add(DateUtil.format(tmp,"MM/dd"));
                qw.between("time",tmp,DateUtil.offsetDay(tmp,1));
            }
            ArrayList<SpendPojo> spojos = (ArrayList<SpendPojo>)spenddao.selectList(qw);
            double count = 0;
            for(SpendPojo spojo : spojos) {
                count += spojo.getValue();
            }
            data.add(count);
        }
        if(len % 2 != 0) {
            tmp_len += 1;
        }
        for(int i = 0; i < tmp_len; i++) {
            DateTime tmp;
            QueryWrapper<SpendPojo> qw = new QueryWrapper<>();
            if(flag.equals("month")) {
                tmp = DateUtil.offsetMonth(t,i + tmp_len);
                x.add(DateUtil.format(tmp,"yyyy/MM"));
                qw.between("time",tmp,DateUtil.offsetMonth(tmp,1));
            }
            else if(flag.equals("year")){
                tmp = DateUtil.offset(t, DateField.YEAR,i + tmp_len);
                x.add(DateUtil.format(tmp,"yyyy"));
                qw.between("time",tmp,DateUtil.offset(tmp,DateField.YEAR,1));
            }
            else{
                tmp = DateUtil.offsetDay(t,i + tmp_len);
                x.add(DateUtil.format(tmp,"MM/dd"));
                qw.between("time",tmp,DateUtil.offsetDay(tmp,1));
            }
            ArrayList<SpendPojo> spojos = (ArrayList<SpendPojo>)spenddao.selectList(qw);
            double count = 0;
            for(SpendPojo spojo : spojos) {
                count += spojo.getValue();
            }
            data.add(count);
        }
        res.put("x",x);
        res.put("data",data);
        return  res;
    }
//    public void delUserBatch(ArrayList<Integer> res,String username,String froway) {
//        for(Integer i : res) {
//            delUser();
//        }
//    }
}
