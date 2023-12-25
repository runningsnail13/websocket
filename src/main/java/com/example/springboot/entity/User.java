package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author snail
 * @since 2023-12-03
 */
@Getter
@Setter
@TableName("registered_user")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
      @TableId(value = "user_id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户名
     */
      @TableField(value = "user_name")
      private String username;

      /**
     * 密码
     */
//      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      @TableField(value = "user_password")
      private String password;

      /**
     * 昵称
     */
      @TableField(value = "user_nickname")
      private String nickname;

      /**
     * 邮箱
     */@TableField(value = "user_email")
      private String email;

      /**
     * 手机号
     */@TableField(value = "user_phone")
      private String phone;

      /**
     * 地址
     */@TableField(value = "user_address")
      private String address;

      /**
     * 创建时间
     */@TableField(value = "creat_time")
      private LocalDateTime createTime;

      /**
     * 头像
     */@TableField(value = "user_avatar")
      private String avatarUrl;

    /**
     * 年龄
     */@TableField(value = "user_age")
    private String age;

    /**
     * 学校
     */@TableField(value = "user_school")
    private String school;

}
