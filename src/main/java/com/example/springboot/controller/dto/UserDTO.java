package com.example.springboot.controller.dto;

/**
 *  接受前端登录请求的参数
 */

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
@Data
public class UserDTO {//负责统一前后端数据的交互，User对象用于映射数据库表项，最好要分开
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private  String nickname;

    /**
     * 头像
     */

    private String avatarUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */private String age;

    /**
     * 学校
     */private String school;

    /**
     * sessionId
     */private String sessionId;

}
