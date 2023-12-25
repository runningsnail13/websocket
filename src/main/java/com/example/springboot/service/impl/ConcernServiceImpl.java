package com.example.springboot.service.impl;

import com.example.springboot.entity.Concern;
import com.example.springboot.mapper.ConcernMapper;
import com.example.springboot.service.IConcernService;
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
public class ConcernServiceImpl extends ServiceImpl<ConcernMapper, Concern> implements IConcernService {

}
