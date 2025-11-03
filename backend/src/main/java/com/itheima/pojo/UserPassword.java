package com.itheima.pojo;

import lombok.Data;

@Data
public class UserPassword {
    private Integer id;
    private String oldPassword;//旧密码
    private String newPassword;//新密码
}
