/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.3-rc-log : Database - JaxwayDB
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`JaxwayDB` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `JaxwayDB`;

/*Table structure for table `GroupUserLink` */

DROP TABLE IF EXISTS `GroupUserLink`;

CREATE TABLE `GroupUserLink` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `group_user` (`user_id`,`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `JaxwayAppInfo` */

DROP TABLE IF EXISTS `JaxwayAppInfo`;

CREATE TABLE `JaxwayAppInfo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_name` varchar(100) DEFAULT NULL,
  `app_despc` varchar(200) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `jaxway_server_id` int(11) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `JaxwayAuthority` */

DROP TABLE IF EXISTS `JaxwayAuthority`;

CREATE TABLE `JaxwayAuthority` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'versionId',
  `jaxway_server_id` int(11) DEFAULT NULL,
  `jaxway_app_id` int(11) DEFAULT NULL,
  `path_pattern` varchar(100) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `op_type` tinyint(5) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `JaxwayGroup` */

DROP TABLE IF EXISTS `JaxwayGroup`;

CREATE TABLE `JaxwayGroup` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) DEFAULT NULL,
  `group_despc` varchar(200) DEFAULT NULL,
  `jaxway_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `server_group` (`jaxway_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `JaxwayRoute` */

DROP TABLE IF EXISTS `JaxwayRoute`;

CREATE TABLE `JaxwayRoute` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '对应 versionId',
  `route_id` varchar(64) DEFAULT NULL,
  `jaxway_server_id` int(11) DEFAULT NULL,
  `route_content` varchar(500) DEFAULT NULL COMMENT 'json 格式化内容',
  `predicate_type` varchar(50) DEFAULT NULL,
  `predicate_value` varchar(200) DEFAULT NULL,
  `filter_type` varchar(50) DEFAULT NULL,
  `filter_value` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `op_type` tinyint(5) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `routeId` (`route_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `JaxwayServer` */

DROP TABLE IF EXISTS `JaxwayServer`;

CREATE TABLE `JaxwayServer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `jaxway_name` varchar(100) NOT NULL,
  `jaxway_despc` varchar(200) DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `JaxwayWhiteList` */

DROP TABLE IF EXISTS `JaxwayWhiteList`;

CREATE TABLE `JaxwayWhiteList` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `jaxway_server_id` int(11) DEFAULT NULL,
  `path_pattern` varchar(100) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `op_type` tinyint(5) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `User` */

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `psw` varchar(200) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL,
  `role_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '用户类型 0-普通用户 1-管理员通用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `UserRoles` */

DROP TABLE IF EXISTS `UserRoles`;

CREATE TABLE `UserRoles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(60) DEFAULT NULL,
  `role_type` tinyint(3) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
