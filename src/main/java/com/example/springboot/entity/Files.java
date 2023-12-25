package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("video_table")
public class Files {

    @TableId(value = "resource_id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "topic")
    private String name;

    private String type;

    private Long size;

    @TableField(value = "video_url")
    private String url;

    private String md5;
    private Boolean isDelete;

    @TableField(value = "resource_state")
    private Boolean enable;

    @TableField(value = "upload_time")
    private LocalDateTime createTime;

    @TableField(value = "resource_user_id")
    private Integer resourceUserId;

    @TableField(value = "like_number")
    private Integer likeNumber;

    @TableField(value = "star_number")
    private Integer starNumber;

    @TableField(value = "coin_number")
    private Integer coinNumber;
}
