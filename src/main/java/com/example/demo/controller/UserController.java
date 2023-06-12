package com.example.demo.controller;

import com.example.demo.dao.BalanceDao;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCodeMsg;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.BalanceService;
import com.example.demo.service.RoleMenuService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.tools.Check;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.Result;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    UserService userservice;
    @Autowired
    RoleService roleservice;
    @Autowired
    BalanceService balanceservice;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/page", method = RequestMethod.GET)
    public String findPage(@RequestParam("pageNum") int i,
                           @RequestParam("pageSize") int j,
                           @RequestParam(value = "username", defaultValue = "", required = false) String username,
                           @RequestParam(value = "role", defaultValue = "", required = false) String role,
                           @RequestParam(value = "currole") String currole){
        int total = userservice.count(username,role,currole);
        Object onePage = userservice.selectByPage(i, j, username, role , currole);
        Map<String, Object> result = new HashMap<>();
        result.put("count", total);
        result.put("data", onePage);
        return Result.okGetStringByData("ok", result);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser(@RequestBody Map<String,String> map) {
        userservice.addUser(map.get("username"),map.get("password"),map.get("role"));
        return Result.okGetString("ok");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editUser(@RequestBody Map<String,String> map) {
        userservice.editUser(Integer.parseInt(map.get("id")),map.get("username"),map.get("password"),map.get("role"));
        return Result.okGetString("ok");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/del", method = RequestMethod.DELETE)
    public String delUser(@RequestParam("id") Integer id) {
        userservice.delUser(id);
        return Result.okGetString("ok");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/delbatch", method = RequestMethod.DELETE)
    public String delUser(@RequestBody Integer [] ids) {
        ArrayList<Integer> res = new ArrayList<>(Arrays.asList(ids));
        userservice.delUserBatch(res);
        return Result.okGetString("ok");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/checkusername", method = RequestMethod.POST)
    public String check_username(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        if (!Check.check_username(username)) {
            throw new AppException(AppExceptionCodeMsg.ERROR_USERNAME);
        }//检测用户名是否为空 是否含有空字符 是否含有除_ . 的特殊字符！
        if (userservice.usernameexist(username)) {
            throw new AppException(AppExceptionCodeMsg.USER_EXISTS);
        }
        return Result.okGetString("ok");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (!Check.check_username(username)) {
            throw new AppException(AppExceptionCodeMsg.ERROR_USERNAME);
        }
        userservice.addUser(username,password,"普通用户");
        return Result.okGetString("ok");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String check_login(@RequestBody Map<String, String> map) {
        String password = map.get("password");
        String username = map.get("username");
        if (!Check.check_username(username) || !userservice.usernameexist(username)) {
            throw new AppException(AppExceptionCodeMsg.USER_NOT_EXISTS);
        }//检测用户名是否为空 是否含有空字符 是否含有除_ . 的特殊字符！
        if (!Check.check_password(password) || !userservice.checkpassword(username, password)) {
            throw new AppException(AppExceptionCodeMsg.PASSWORD_ERROR);
        }
        UserPojo upojo = (UserPojo)userservice.getuserinfo(username);
        ArrayList<Integer> menukeys = roleservice.findrolemenubyname(upojo.getRole());
        Map<String,Object> res = new HashMap<>();
        res.put("menu",menukeys);
        res.put("data",upojo);
        res.put("token", TokenUtils.genToken(username,password));
        return Result.okGetStringByData("ok",res);
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(value = "/front/user", method = RequestMethod.GET)
//    public String findpage_front(@RequestParam("pageNum") int i,
//                                 @RequestParam("pageSize") int j,
//                                 @RequestParam("familyid")) {
//        int total = userservice.count(username,);
//        Object onePage = userservice.selectByPage(i, j, username,);
//        Map<String, Object> result = new HashMap<>();
//        result.put("count", total);
//        result.put("data", onePage);
//        return Result.okGetStringByData("ok", result);
//    }

}