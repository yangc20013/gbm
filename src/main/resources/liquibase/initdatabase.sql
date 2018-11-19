SET FOREIGN_KEY_CHECKS=0;

/**
CREATE DATABASE IF NOT EXISTS yang_gbm default charset utf8 COLLATE utf8_general_ci;
*/

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `telephone` varchar(16) DEFAULT NULL COMMENT '住宅电话',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `is_male` tinyint(1) DEFAULT '1' COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `email` varchar(50) DEFAULT NULL COMMENT 'Email',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `user_type` tinyint(1) DEFAULT NULL COMMENT '用户级别，1:可以后端登录',
  `remarks` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) default null,
  `create_time` datetime default null,
  `update_time` datetime default null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
INSERT INTO `user` VALUES ('1', 'admin','$2a$04$IRJbb4UnA0iS5iVqdeTe0uU59qqFw2l/Oaso9mSNNxO6fPezhcF3.', '系统管理员', '18568887789', '029-82881234', 30, '1','四川成都','admin@163.com',
'http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg', '1',1,'系统管理员',null,now(),null);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_roleName` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES ('1', 'admin', '系统管理员');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_role_ibfk_1` (`user_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
INSERT INTO `user_role` VALUES ('1', '1', '1');

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `sort` int default 0,
  `name` varchar(64) DEFAULT NULL,
  `href` varchar(64) DEFAULT NULL,
  `icon` varchar(64) DEFAULT NULL,
  `type` int default null,
  `usable` tinyint(1) DEFAULT '1',
  `description` varchar(255) default null,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

INSERT INTO `menu` VALUES (1, null, 0, '仪表盘', '/index', 'fa fa-dashboard',1, 1, null);
INSERT INTO `menu` VALUES (2, null, 0, '系统管理', '/sys', 'fa fa-cog',1, 1, null);
INSERT INTO `menu` VALUES (3, 2, 0, '菜单管理', '/sys/menuList', 'fa fa-navicon',1, 1, null);
INSERT INTO `menu` VALUES (4, 2, 0, '角色管理', '/sys/roleList', 'fa fa-universal-access',1, 1, null);
INSERT INTO `menu` VALUES (5, 2, 0, '用户管理', '/sys/userList', 'fa fa-user-plus',1, 1, null);


DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `menu_id` (`menu_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

INSERT INTO `menu_role` VALUES (1, '1', '1');
INSERT INTO `menu_role` VALUES (2, '2', '1');
INSERT INTO `menu_role` VALUES (3, '3', '1');
INSERT INTO `menu_role` VALUES (4, '4', '1');
INSERT INTO `menu_role` VALUES (5, '5', '1');
INSERT INTO `menu_role` VALUES (6, '6', '1');

SET FOREIGN_KEY_CHECKS=1;
