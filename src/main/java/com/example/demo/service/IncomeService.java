package com.example.demo.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.IncomeDao;
import com.example.demo.dao.UserDao;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.pojo.BalancePojo;
import com.example.demo.pojo.IncomePojo;
import com.example.demo.pojo.SpendPojo;
import com.example.demo.pojo.UserPojo;
import com.sun.org.apache.xpath.internal.objects.XObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class IncomeService {
    @Autowired
    IncomeDao incomedao;
    @Autowired
    BalanceDao balancedao;
    @Autowired
    UserDao userdao;
    public int count(String username,String kind, String fromway,Date begin,Date end)
    {
        return incomedao.count(username,kind,fromway,begin,end);
    }

    public Object selectByPage(int i, int j, String username, String kind, String fromway,Date begin,Date end) {
        int ItemNumber = (i - 1) * j;
        return incomedao.findByPage(ItemNumber,j,username,kind,fromway,begin,end);
    }

    public void save(int id, double value, String describe, String kind, String username, Date t,String fromway) {
        QueryWrapper<BalancePojo> qw = new QueryWrapper<>();
        qw.eq("username",username);
        qw.eq("fromway",fromway);
        double v = balancedao.selectOne(qw).getValue();
        IncomePojo pojo = new IncomePojo();
        pojo.setTime(t);
        pojo.setDescription(describe);
        pojo.setKind(kind);
        pojo.setValue(value);
        pojo.setUsername(username);
        pojo.setFromway(fromway);
        if(incomedao.exists(new QueryWrapper<IncomePojo>().eq("id",id))){
            double raw_v = incomedao.selectById(id).getValue();
            v -= raw_v;
            pojo.setId(id);
            incomedao.updateById(pojo);
        }
        else {
            incomedao.insert(pojo);
        }
        v += value;
        balancedao.update(new BalancePojo(username,fromway,v),qw);
    }

    public void delUser(Integer id,String username,String fromway) {
        if(username != null && fromway != null)
        {
            QueryWrapper<BalancePojo> qw = new QueryWrapper<>();
            qw.eq("username",username);
            qw.eq("fromway",fromway);
            double value = balancedao.selectOne(qw).getValue();
            double v = incomedao.selectById(id).getValue();
            value -= v;
            balancedao.update(new BalancePojo(username,fromway,value),qw);
        }
        incomedao.deleteById(id);
    }

    public ArrayList<Double> getweekincome(DateTime weekbegin,String username) {
        ArrayList<Double> res = new ArrayList<>();
        DateTime tmp = weekbegin;
        for(int i = 1; i <= 7; i++) {
            QueryWrapper<IncomePojo> qw = new QueryWrapper<>();
//            qw.ge("time",tmp);
//            qw.lt("time", DateUtil.offsetDay(weekbegin,i));
            qw.eq("username",username);
            qw.between("time",tmp,DateUtil.offsetDay(weekbegin,i));
            ArrayList<IncomePojo> pojos = (ArrayList<IncomePojo>)incomedao.selectList(qw);
            double count = 0;
            for(IncomePojo item : pojos){
                count += item.getValue();
            }
            res.add(count);
            tmp = DateUtil.offsetDay(weekbegin,i);
        }
        return res;
    }
    public Double counttodayincome(String username,DateTime time) {
        QueryWrapper<IncomePojo> qw = new QueryWrapper<>();
        qw.eq("username",username);
        qw.between("time",time,DateUtil.offsetDay(time,1));
        ArrayList<IncomePojo> pojos = (ArrayList<IncomePojo>)incomedao.selectList(qw);
        double count = 0;
        for(IncomePojo pojo : pojos){
            count += pojo.getValue();
        }
        return count;
    }

    public ArrayList<Map<String,Object>> getoption2(String username, DateTime tbegin, DateTime tend) {
        ArrayList<Map<String,Object>> res = new ArrayList<>();
        ArrayList<String> incomekind = new ArrayList<>();
        incomekind.add("工资");
        incomekind.add("投资");
        incomekind.add("其他");
        incomekind.add("人情");
        for(String i:incomekind) {
            QueryWrapper<IncomePojo> qw = new QueryWrapper<>();
            if(!StrUtil.isBlank(username)) {
                qw.eq("username", username);
            }
            if(tbegin != null) {
                qw.between("time", tbegin, tend);
            }
            qw.eq("kind",i);
            ArrayList<IncomePojo> pojos = (ArrayList<IncomePojo>)incomedao.selectList(qw);
            double count = 0;
            for(IncomePojo pojo: pojos){
                count += pojo.getValue();
            }
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("name",i);
            tmp.put("value",count);
            res.add(tmp);
        }
        return res;
    }

    public ArrayList<Map<String,Object>> getincomebyuseroption(DateTime tbegin, DateTime tend) {
        ArrayList<Map<String,Object>> res = new ArrayList<>();
        ArrayList<UserPojo> upojos = (ArrayList<UserPojo>)userdao.selectList(new QueryWrapper<>());
        for(UserPojo upojo : upojos){
            QueryWrapper<IncomePojo> qw = new QueryWrapper<>();
            qw.eq("username",upojo.getUsername());
            if(tbegin != null) {
                qw.between("time",tbegin,tend);
            }
            double count = 0;
            ArrayList<IncomePojo> ipojos = (ArrayList<IncomePojo>)incomedao.selectList(qw);
            for(IncomePojo ipojo : ipojos) {
                count += ipojo.getValue();
            }
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("name",upojo.getUsername());
            tmp.put("value",count);
            res.add(tmp);
        }
        return res;
    }

    public Map<String,Object> getoption4(DateTime t, String flag, int len) {
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
            QueryWrapper<IncomePojo> qw = new QueryWrapper<>();
            if(flag.equals("month")) {
                tmp = DateUtil.offsetMonth(t,i - tmp_len);
                x.add(DateUtil.format(tmp,"yyyy/MM"));
                qw.between("time",tmp,DateUtil.offsetMonth(tmp,1));
            }
            else if(flag.equals("year")){
                tmp = DateUtil.offset(t,DateField.YEAR,i - tmp_len);
                x.add(DateUtil.format(tmp,"yyyy"));
                qw.between("time",tmp,DateUtil.offset(tmp,DateField.YEAR,1));
            }
            else{
                tmp = DateUtil.offsetDay(t,i - tmp_len);
                x.add(DateUtil.format(tmp,"MM/dd"));
                qw.between("time",tmp,DateUtil.offsetDay(tmp,1));
            }
            ArrayList<IncomePojo> ipojos = (ArrayList<IncomePojo>)incomedao.selectList(qw);
            double count = 0;
            for(IncomePojo ipojo : ipojos) {
                count += ipojo.getValue();
            }
            data.add(count);
        }
        if(len % 2 != 0) {
            tmp_len += 1;
        }
        for(int i = 0; i < tmp_len; i++) {
            DateTime tmp;
            QueryWrapper<IncomePojo> qw = new QueryWrapper<>();
            if(flag.equals("month")) {
                tmp = DateUtil.offsetMonth(t,i + tmp_len);
                x.add(DateUtil.format(tmp,"yyyy/MM"));
                qw.between("time",tmp,DateUtil.offsetMonth(tmp,1));
            }
            else if(flag.equals("year")){
                tmp = DateUtil.offset(t,DateField.YEAR,i + tmp_len);
                x.add(DateUtil.format(tmp,"yyyy"));
                qw.between("time",tmp,DateUtil.offset(tmp,DateField.YEAR,1));
            }
            else{
                tmp = DateUtil.offsetDay(t,i + tmp_len);
                x.add(DateUtil.format(tmp,"MM/dd"));
                qw.between("time",tmp,DateUtil.offsetDay(tmp,1));
            }
            ArrayList<IncomePojo> ipojos = (ArrayList<IncomePojo>)incomedao.selectList(qw);
            double count = 0;
            for(IncomePojo ipojo : ipojos) {
                count += ipojo.getValue();
            }
            data.add(count);
        }
        res.put("x",x);
        res.put("data",data);
        return  res;
    }

//    public void delUserBatch(ArrayList<Integer> res) {
//        incomedao.deleteBatchIds(res);
//    }
}
