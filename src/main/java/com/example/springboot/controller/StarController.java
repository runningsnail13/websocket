package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.IStarService;
import com.example.springboot.entity.Star;

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
@RequestMapping("//star")
        public class StarController {
    
    @Resource
    private IStarService starService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Star star) {
        return starService.saveOrUpdate(star);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return starService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return starService.removeByIds(ids);
    }

    @GetMapping
    public List<Star> findAll() {
        return starService.list();
    }

    @GetMapping("/{id}")
    public Star findOne(@PathVariable Integer id) {
        return starService.getById(id);
    }

    @GetMapping("/page")
    public Page<Star> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return starService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    }

