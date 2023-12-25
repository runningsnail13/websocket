package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.ICoinService;
import com.example.springboot.entity.Coin;

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
@RequestMapping("//coin")
public class CoinController {
    
    @Resource
    private ICoinService coinService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Coin coin) {
        return coinService.saveOrUpdate(coin);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return coinService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return coinService.removeByIds(ids);
    }

    @GetMapping
    public List<Coin> findAll() {
        return coinService.list();
    }

    @GetMapping("/{id}")
    public Coin findOne(@PathVariable Integer id) {
        return coinService.getById(id);
    }

    @GetMapping("/page")
    public Page<Coin> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Coin> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return coinService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

