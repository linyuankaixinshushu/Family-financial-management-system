package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.IncomePojo;
import com.example.demo.pojo.SpendPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface IncomeDao extends BaseMapper<IncomePojo> {
    List<SpendPojo> findByPage(int page_num, int j , String username, String kind ,String fromway,Date begin,Date end);

    int count(String username, String kind, String fromway, Date begin, Date end);
}
