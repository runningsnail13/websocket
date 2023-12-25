package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.IConcernService;
import com.example.springboot.entity.Concern;

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
@RequestMapping("//concern")
        public class ConcernController {
    
    @Resource
    private IConcernService concernService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Concern concern) {
        return concernService.saveOrUpdate(concern);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return concernService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return concernService.removeByIds(ids);
    }

    @GetMapping
    public List<Concern> findAll() {
        return concernService.list();
    }

    @GetMapping("/{id}")
    public Concern findOne(@PathVariable Integer id) {
        return concernService.getById(id);
    }

    @GetMapping("/page")
    public Page<Concern> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Concern> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return concernService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    }

