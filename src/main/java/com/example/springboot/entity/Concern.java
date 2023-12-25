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
  public class Concern implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 关注者的ID（用户编号）
     */
      @MppMultiId
      private Integer followerId;

      /**
     * 被关注者的ID（用户编号）
     */
      @MppMultiId
      private Integer followeredId;

      /**
     * 关注时间
     */
      private LocalDateTime followerTime;
}
