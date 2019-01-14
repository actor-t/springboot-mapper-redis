/*
Navicat MySQL Data Transfer

Source Server         : ykttest
Source Server Version : 50720
Source Host           : 47.92.97.192:3306
Source Database       : ykttest

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-04 15:55:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `real_name` varchar(20) NOT NULL COMMENT '真实姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `role` varchar(10) DEFAULT NULL COMMENT '角色类型',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(20) NOT NULL COMMENT '电话',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `photourl` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_sig` varchar(500) DEFAULT NULL COMMENT '腾讯用户sig',
  `sign_url` varchar(255) DEFAULT NULL COMMENT '签名信息url',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '微信用户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
