package com.example.springboot.service.impl;

import com.example.springboot.entity.Message;
import com.example.springboot.mapper.MessageMapper;
import com.example.springboot.service.IMessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
