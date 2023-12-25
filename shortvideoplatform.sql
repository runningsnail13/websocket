/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44-log)
 Source Host           : localhost:3306
 Source Schema         : shortvideoplatform

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44-log)
 File Encoding         : 65001

 Date: 12/12/2023 15:23:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator_table
-- ----------------------------
DROP TABLE IF EXISTS `administrator_table`;
CREATE TABLE `administrator_table`  (
  `admin_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '管理员编号',
  `admin_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员头像',
  `admin_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `admin_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_conversation
-- ----------------------------
DROP TABLE IF EXISTS `chat_conversation`;
CREATE TABLE `chat_conversation`  (
  `conversation_id` int(20) NOT NULL AUTO_INCREMENT,
  `sender_id` int(20) NULL DEFAULT NULL,
  `receiver_id` int(20) NULL DEFAULT NULL,
  `latest_message_id` int(20) NULL DEFAULT NULL,
  `latest_message_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`conversation_id`) USING BTREE,
  INDEX `sender_id`(`sender_id`) USING BTREE,
  INDEX `receiver_id`(`receiver_id`) USING BTREE,
  INDEX `latest_message_id`(`latest_message_id`) USING BTREE,
  CONSTRAINT `chat_conversation_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `chat_conversation_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `registered_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `chat_conversation_ibfk_3` FOREIGN KEY (`latest_message_id`) REFERENCES `message` (`message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for coin
-- ----------------------------
DROP TABLE IF EXISTS `coin`;
CREATE TABLE `coin`  (
  `user_id` int(20) NOT NULL,
  `resource_id` int(20) NOT NULL,
  `coin_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `resource_id`) USING BTREE,
  INDEX `resource_id`(`resource_id`) USING BTREE,
  CONSTRAINT `coin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `coin_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `video_table` (`resource_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int(64) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `resource_id` int(20) NULL DEFAULT NULL COMMENT '视频编号',
  `user_id` int(20) NULL DEFAULT NULL COMMENT '用户编号',
  `comment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
  `comment_state` int(2) NULL DEFAULT NULL COMMENT '评论状态',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `resource_id`(`resource_id`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `video_table` (`resource_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for concern
-- ----------------------------
DROP TABLE IF EXISTS `concern`;
CREATE TABLE `concern`  (
  `follower_id` int(20) NOT NULL COMMENT '关注者的ID（用户编号）',
  `followered_id` int(20) NOT NULL COMMENT '被关注者的ID（用户编号）',
  `follower_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
  PRIMARY KEY (`follower_id`, `followered_id`) USING BTREE,
  INDEX `followered_id`(`followered_id`) USING BTREE,
  CONSTRAINT `concern_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `concern_ibfk_2` FOREIGN KEY (`followered_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `user_id` int(20) NOT NULL,
  `resource_id` int(20) NOT NULL,
  `like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `resource_id`) USING BTREE,
  INDEX `resource_id`(`resource_id`) USING BTREE,
  CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `video_table` (`resource_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `conversation_id` int(20) NULL DEFAULT NULL COMMENT '会话ID',
  `sender_id` int(20) NULL DEFAULT NULL COMMENT '发送者ID',
  `receiver_id` int(20) NULL DEFAULT NULL COMMENT '接收者ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '消息内容',
  `send_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `sender_id`(`sender_id`) USING BTREE,
  INDEX `receiver_id`(`receiver_id`) USING BTREE,
  INDEX `conversation_id`(`conversation_id`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `message_ibfk_3` FOREIGN KEY (`conversation_id`) REFERENCES `chat_conversation` (`conversation_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for registered_user
-- ----------------------------
DROP TABLE IF EXISTS `registered_user`;
CREATE TABLE `registered_user`  (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `user_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录密码',
  `user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `user_age` int(2) NULL DEFAULT NULL COMMENT '年龄',
  `user_gender` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '性别默认为0表示男',
  `user_birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生日',
  `user_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `user_school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学校',
  `user_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `user_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个性签名',
  `user_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star`  (
  `user_id` int(20) NOT NULL,
  `resource_id` int(20) NOT NULL,
  `star_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `resource_id`) USING BTREE,
  INDEX `resource_id`(`resource_id`) USING BTREE,
  CONSTRAINT `star_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `star_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `video_table` (`resource_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下载链接',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用链接',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件md5',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1032 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for video_table
-- ----------------------------
DROP TABLE IF EXISTS `video_table`;
CREATE TABLE `video_table`  (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频编号',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '视频标题',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '视频描述',
  `upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `resource_user_id` int(20) NULL DEFAULT NULL COMMENT '上传用户ID',
  `like_number` int(20) UNSIGNED NULL DEFAULT NULL COMMENT '点赞数',
  `coin_number` int(20) UNSIGNED NULL DEFAULT NULL COMMENT '投币数',
  `star_number` int(20) UNSIGNED NULL DEFAULT NULL COMMENT '收藏数',
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件md5',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `resource_state` tinyint(1) NULL DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`resource_id`) USING BTREE,
  INDEX `topic`(`topic`) USING BTREE,
  INDEX `resource_user_id`(`resource_user_id`) USING BTREE,
  CONSTRAINT `video_table_ibfk_1` FOREIGN KEY (`resource_user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for visitor_table
-- ----------------------------
DROP TABLE IF EXISTS `visitor_table`;
CREATE TABLE `visitor_table`  (
  `visitor_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '访客编号',
  `visit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  PRIMARY KEY (`visitor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
