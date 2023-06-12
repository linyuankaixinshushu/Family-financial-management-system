package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "users")
public class UserPojo {
    @TableId (value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField (value = "username")
    private String username;
    @TableField (value = "password")
    private String password;
    @TableField (value = "role")
    private String role;
    public UserPojo(String username_, String password_,String role_)
    {
        username = username_;
        password = password_;
        role = role_;
    }
}
