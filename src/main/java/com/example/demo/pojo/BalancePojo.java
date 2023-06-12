package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "balance")
public class BalancePojo {
    @TableField("username")
    private String username;
    @TableField("fromway")
    private String fromway;
    @TableField("value")
    private double value;
}
