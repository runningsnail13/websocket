package com.example.springboot.service.impl;

import com.example.springboot.entity.ChatConversation;
import com.example.springboot.mapper.ChatConversationMapper;
import com.example.springboot.service.IChatConversationService;
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
public class ChatConversationServiceImpl extends ServiceImpl<ChatConversationMapper, ChatConversation> implements IChatConversationService {

}
