package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.IChatConversationService;
import com.example.springboot.entity.ChatConversation;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author snail
 * @since 2023-12-09
 */
@RestController
@RequestMapping("//chat-conversation")
        public class ChatConversationController {
    
    @Resource
    private IChatConversationService chatConversationService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody ChatConversation chatConversation) {
        return chatConversationService.saveOrUpdate(chatConversation);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return chatConversationService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return chatConversationService.removeByIds(ids);
    }

    @GetMapping
    public List<ChatConversation> findAll() {
        return chatConversationService.list();
    }

    @GetMapping("/{id}")
    public ChatConversation findOne(@PathVariable Integer id) {
        return chatConversationService.getById(id);
    }

    @GetMapping("/page")
    public Page<ChatConversation> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<ChatConversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return chatConversationService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    }

