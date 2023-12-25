package com.example.springboot.service.impl;

import com.example.springboot.entity.Likes;
import com.example.springboot.mapper.LikeMapper;
import com.example.springboot.service.ILikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snail
 * @since 2023-12-09
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Likes> implements ILikeService {

}
