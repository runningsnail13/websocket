package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.IMessageService;
import com.example.springboot.entity.Message;

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
@RequestMapping("//message")
public class MessageController {
    
    @Resource
    private IMessageService messageService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Message message) {
        return messageService.saveOrUpdate(message);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return messageService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return messageService.removeByIds(ids);
    }

    @GetMapping
    public List<Message> findAll() {
        return messageService.list();
    }

    @GetMapping("/{id}")
    public Message findOne(@PathVariable Integer id) {
        return messageService.getById(id);
    }

    @GetMapping("/page")
    public Page<Message> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return messageService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    }

