CREATE DATABASE `van_news` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `headpic` varchar(200) DEFAULT NULL,
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL,
  `createdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
CREATE TABLE `t_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `pic` varchar(200) DEFAULT NULL,
  `source` varchar(500) DEFAULT NULL,
  `tags` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `content` text,
  `count_comment` int(11) DEFAULT NULL,
  `count_like` int(11) DEFAULT NULL,
  `count_browser` int(11) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL,
  `check_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资讯表';
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to_id` int(11) DEFAULT NULL,
  `to_type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `user_headpic` varchar(200) DEFAULT NULL,
  `content` text,
  `createdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';
