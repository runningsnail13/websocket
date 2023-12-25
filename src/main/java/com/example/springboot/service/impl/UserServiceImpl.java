package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snail
 * @since 2023-12-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO, HttpSession session) {
        User one = getUserInfo(userDTO);//查找
        if (one != null) {
            session.setAttribute(session.getId(),one);
            session.setMaxInactiveInterval(60*60*24);
            BeanUtil.copyProperties(one, userDTO, true);//将实体类对象拷贝到UserDTO中并返回
            userDTO.setSessionId(session.getId());
            System.out.println("sessionGetId："+session.getId());
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
            //全局异常处理器中的handle方法会捕获到这个ServiceException异常，并将该异常对象作为参数传递给handle方法进行处理
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfoUsername(userDTO);
        if (one == null) {//查询无结果
            one = new User();//新建一个
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");//发现用户已存在抛出异常
        }
        return one;
    }

    private User getUserInfo(UserDTO userDTO) {//根据用户名和密码查询用户信息并返回
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userDTO.getUsername());
        queryWrapper.eq("user_password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
    private User getUserInfoUsername(UserDTO userDTO) {//根据用户名查询用户信息并返回
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userDTO.getUsername());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

}

