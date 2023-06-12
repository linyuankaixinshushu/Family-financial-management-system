package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.BalanceDao;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.pojo.BalancePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BalanceService {
    @Autowired
    BalanceDao balancedao;

    public int count() {
        return balancedao.count();
    }

    public Object selectByPage(int pageNum, int pageSize) {
        int ItemNumber = (pageNum - 1) * pageSize;
        return balancedao.findByPage(ItemNumber,pageSize);
    }

    public void addfromway(String username, String fromway, double value) {
        if(existfromway(username,fromway)) {
            throw new AppException(AppExceptionCodeMsg.EXIST_FROM);
        }
        balancedao.insert(new BalancePojo(username,fromway,value));
    }

    public boolean existfromway(String username,String fromway) {
        QueryWrapper<BalancePojo> qw = new QueryWrapper<>();
        qw.eq("username",username);
        qw.eq("fromway",fromway);
        return balancedao.exists(qw);
    }

    public void delfromway(String username, String fromway) {
        QueryWrapper<BalancePojo> qw = new QueryWrapper<>();
        qw.eq("username",username);
        qw.eq("fromway",fromway);
        balancedao.delete(qw);
    }

    public ArrayList<String> finduserhasway(String username) {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<BalancePojo> balancePojos = (ArrayList<BalancePojo>)balancedao.selectList(
                new QueryWrapper<BalancePojo>().eq("username",username));
        for(BalancePojo i : balancePojos) {
            res.add(i.getFromway());
        }
        return res;
    }
}
