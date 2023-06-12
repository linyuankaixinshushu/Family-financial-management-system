package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.RoleMenuDao;
import com.example.demo.dao.UserDao;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.pojo.RoleMenuPojo;
import com.example.demo.pojo.RolePojo;
import com.example.demo.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleDao roledao;
    @Autowired
    UserDao userdao;
    @Autowired
    RoleMenuDao rolemenudao;

    public Object selectByPage(int i, int j , String currole) {
        int ItemNumber = (i - 1) * j;
        return roledao.findByPage(ItemNumber,j ,currole);
    }

    public int count(String currole) {
        return roledao.count(currole);
    }

    public boolean idexist(int id)
    {
        return roledao.exists(new QueryWrapper<RolePojo>().eq("id",id));
    }

//    public boolean nameexist(String name) {
//        QueryWrapper<RolePojo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name",name);
//        return roledao.exists(queryWrapper);
//    }

    public boolean update(int id,String name,String describe) {
        RolePojo pojo = new RolePojo();
        pojo.setName(name);
        pojo.setDescription(describe);
        if(idexist(id)) {
            pojo.setId(id);
            roledao.updateById(pojo);
        }
        else {
            roledao.insert(pojo);
        }
        return true;
    }

    public void delUser(Integer id) {
        if(id == 1 || id == 2) {
            throw new AppException(AppExceptionCodeMsg.DEFAULT_ROLE);
        }
        String name = roledao.selectById(id).getName();
        if(userdao.exists(new QueryWrapper<UserPojo>().eq("role",name))) {
            throw new AppException(AppExceptionCodeMsg.DELROLEFAILED);
        }
        rolemenudao.delete(new QueryWrapper<RoleMenuPojo>().eq("roleid",id));
        roledao.deleteById(id);
    }

    public ArrayList<Integer> findrolemenubyname(String role) {
        int roleid = roledao.selectOne(new QueryWrapper<RolePojo>().eq("name",role)).getId();
        ArrayList<RoleMenuPojo> rolemenuPojos = (ArrayList<RoleMenuPojo>) rolemenudao.selectList(new QueryWrapper<RoleMenuPojo>().eq("roleid",roleid));
        ArrayList<Integer> res = new ArrayList<>();
        for(RoleMenuPojo i : rolemenuPojos){
            res.add(i.getMenuid());
        }
        return res;
    }
}
