package com.example.demo.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "income")
public class IncomePojo {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "value")
    private double value;
    @TableField(value = "time")
    private Date time;
    @TableField(value = "description")
    private String description;
    @TableField(value = "kind")
    private String kind;
    @TableField(value = "username")
    private String username;
    @TableField(value = "fromway")
    private String fromway;
}
