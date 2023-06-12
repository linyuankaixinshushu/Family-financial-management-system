package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.RoleMenuDao;
import com.example.demo.pojo.RoleMenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuService {
    @Autowired
    RoleMenuDao rolemenudao;

    public void setrolemenu(int id,int [] mids){
        rolemenudao.delete(new QueryWrapper<RoleMenuPojo>().eq("roleid",id));
        for (int mid : mids) {
            rolemenudao.insert(new RoleMenuPojo(id, mid));
        }
    }
    public Object findmenurolehas (int id) {
        return rolemenudao.selectList(new QueryWrapper<RoleMenuPojo>().eq("roleid",id));
    }
}
