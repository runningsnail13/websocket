package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
  public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 评论ID
     */
      @TableId(value = "comment_id", type = IdType.AUTO)
      private Integer commentId;

      /**
     * 视频编号
     */
      private Integer resourceId;

      /**
     * 用户编号
     */
      private Integer userId;

      /**
     * 评论时间
     */
      private LocalDateTime commentTime;

      /**
     * 评论内容
     */
      private String content;

      /**
     * 评论状态
     */
      private Integer commentState;
}
