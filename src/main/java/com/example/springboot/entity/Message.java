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
  public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 消息ID
     */
        @TableId(value = "message_id", type = IdType.AUTO)
      private Integer messageId;

      /**
     * 会话ID
     */
      private Integer conversationId;

      /**
     * 发送者ID
     */
      private Integer senderId;

      /**
     * 接收者ID
     */
      private Integer receiverId;

      /**
     * 消息内容
     */
      private String content;

      /**
     * 发送时间
     */
      private LocalDateTime sendTime;
}
