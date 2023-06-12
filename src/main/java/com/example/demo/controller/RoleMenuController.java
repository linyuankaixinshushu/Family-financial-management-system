package com.example.demo.controller;

import com.example.demo.service.RoleMenuService;
import com.example.demo.service.RoleService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RoleMenuController {
    @Autowired
    RoleMenuService rolemenuservice;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/role/rolemenu/{id}", method = RequestMethod.POST)
    public String rolemenu(@RequestBody int[] mids,@PathVariable int id) {
        rolemenuservice.setrolemenu(id,mids);
        return Result.okGetString("ok");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/role/check/{id}", method = RequestMethod.GET)
    public String findmenurolehas(@PathVariable int id) {
        return Result.okGetStringByData("ok",rolemenuservice.findmenurolehas(id));
    }
}
