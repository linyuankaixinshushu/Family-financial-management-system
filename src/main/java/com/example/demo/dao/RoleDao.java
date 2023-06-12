package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.RolePojo;
import com.example.demo.pojo.SpendPojo;
import com.example.demo.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<RolePojo> {
    List<RoleDao> findByPage(int page_num, int page_size,String currole);

    int count(String currole);
}
