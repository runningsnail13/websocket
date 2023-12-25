package com.example.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author snail
 * @since 2023-12-09
 */
@Getter
@Setter
  public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;
    @MppMultiId
    private Integer userId;
    @MppMultiId
    private Integer resourceId;

    private LocalDateTime likeTime;
}
