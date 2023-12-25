package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.ICommentService;
import com.example.springboot.entity.Comment;

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
@RequestMapping("//comment")
        public class CommentController {
    
    @Resource
    private ICommentService commentService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Comment comment) {
        return commentService.saveOrUpdate(comment);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return commentService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return commentService.removeByIds(ids);
    }

    @GetMapping
    public List<Comment> findAll() {
        return commentService.list();
    }

    @GetMapping("/{id}")
    public Comment findOne(@PathVariable Integer id) {
        return commentService.getById(id);
    }

    @GetMapping("/page")
    public Page<Comment> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return commentService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    }

