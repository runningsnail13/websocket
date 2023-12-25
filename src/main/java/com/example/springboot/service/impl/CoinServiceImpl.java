package com.example.springboot.service.impl;

import com.example.springboot.entity.Coin;
import com.example.springboot.mapper.CoinMapper;
import com.example.springboot.service.ICoinService;
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
public class CoinServiceImpl extends ServiceImpl<CoinMapper, Coin> implements ICoinService {

}
