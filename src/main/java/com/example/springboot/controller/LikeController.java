package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.ILikeService;
import com.example.springboot.entity.Likes;

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
@RequestMapping("/like")
public class LikeController {
    
    @Resource
    private ILikeService likeService;

// 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Likes likes) {
        return likeService.saveOrUpdate(likes);
        }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return likeService.removeById(id);
        }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return likeService.removeByIds(ids);
    }

    @GetMapping
    public List<Likes> findAll() {
        return likeService.list();
    }

    @GetMapping("/{id}")
    public Likes findOne(@PathVariable Integer id) {
        return likeService.getById(id);
    }


    @GetMapping("/tick")//用户的点赞行为
    public Result tickCheckout(@RequestParam Integer resourceId, @RequestParam Integer userId) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_id",resourceId);
        queryWrapper.eq("user_id",userId);
        Likes like = likeService.getOne(queryWrapper); // 查询是否存在该表项

        if (like == null) {
            // 如果不存在，则创建一个新的表项
            like = new Likes();
            like.setResourceId(resourceId);
            like.setUserId(userId);
            likeService.save(like);
            return Result.success();
        }
        return Result.error();
    }

    @GetMapping("/page")
    public Page<Likes> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return likeService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/findLikesByUserId/{userId}")
    public List<Integer> findLikesByUserId(@PathVariable Integer userId) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Likes> tmp=likeService.list(queryWrapper);
        List<Integer> ans=new ArrayList<>();
        for(Likes i:tmp){
            ans.add(i.getResourceId());
        }
        return ans;
    }

}

