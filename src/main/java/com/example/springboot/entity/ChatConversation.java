package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
  @TableName("chat_conversation")
public class ChatConversation implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "conversation_id", type = IdType.AUTO)
      private Integer conversationId;

    private Integer senderId;

    private Integer receiverId;

    private Integer latestMessageId;

    private LocalDateTime latestMessageTime;
}
