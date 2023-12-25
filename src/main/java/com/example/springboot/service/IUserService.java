package com.example.springboot.service;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author snail
 * @since 2023-12-03
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO, HttpSession session);
    User register(UserDTO userDTO);
}
