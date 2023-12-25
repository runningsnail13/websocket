package com.example.springboot.service.impl;

import com.example.springboot.entity.Star;
import com.example.springboot.mapper.StarMapper;
import com.example.springboot.service.IStarService;
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
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements IStarService {

}
