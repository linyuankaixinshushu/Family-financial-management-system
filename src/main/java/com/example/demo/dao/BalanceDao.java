package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.BalancePojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BalanceDao extends BaseMapper<BalancePojo> {
    int count();
    List<Object> findByPage(int itemNumber, int pageSize);
}
