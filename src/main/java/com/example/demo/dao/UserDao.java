package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDao extends BaseMapper<UserPojo> {
    List<UserPojo> findByPage(int page_num, int page_size, String username,String role,String currole);
    int count(String username,String role,String currole);
}
