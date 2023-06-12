package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.IncomeDao;
import com.example.demo.dao.SpendDao;
import com.example.demo.dao.UserDao;
import com.example.demo.pojo.BalancePojo;
import com.example.demo.pojo.IncomePojo;
import com.example.demo.pojo.SpendPojo;
import com.example.demo.pojo.UserPojo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserDao userdao;
    @Autowired
    SpendDao spenddao;
    @Autowired
    IncomeDao incomedao;
    @Autowired
    BalanceDao balancedao;
//    public void addUser(String username, String password) {
//        userdao.insert(new UserPojo(username,password));
//    }
//    public void delUserById(int id) {
//        userdao.deleteById(id);
//    }
//    public void delUserByUsername(String username) {
//        userdao.delete(new QueryWrapper<UserPojo>().like("username",username ));
//    }
//    public Object selectall() {
//        return userdao.selectList(null);
//    }//用于选择所有的用户

    public Object selectByPage(int page_num,int page_size,String username,String role,String currole) {
        int ItemNumber = (page_num - 1) * page_size;
        return userdao.findByPage(ItemNumber,page_size,username,role,currole);
    }

    public int count(String username,String role,String currole)
    {
        return userdao.count(username,role,currole);
    }

    public boolean addUser(String username, String password, String role) {
        int state = userdao.insert(new UserPojo(username,password,role));
        return state != 0;
    }

    public void delUser(Integer id) {
        String username = userdao.selectById(id).getUsername();
        balancedao.delete(new QueryWrapper<BalancePojo>().eq("username",username));
        spenddao.delete(new QueryWrapper<SpendPojo>().eq("username",username));
        incomedao.delete(new QueryWrapper<IncomePojo>().eq("username",username));
        userdao.deleteById(id);
    }
    public void delUserBatch(ArrayList<Integer> ids) {
        for(Integer i : ids){
            delUser(i);
        }
    }
    public boolean usernameexist(String username) {
        return userdao.exists(new QueryWrapper<UserPojo>().eq("username",username));
    }

    public boolean checkpassword(String username , String password) {
        return password.equals(userdao.selectOne(new QueryWrapper<UserPojo>().eq("username",username)).getPassword());
    }



    public Object getuserinfo(String username) {
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userdao.selectOne(queryWrapper);
    }

    public void editUser(int id, String username, String password,String role) {
        userdao.updateById(new UserPojo(id,username,password,role));
    }
}

