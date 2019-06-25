-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.24 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 shiro_test 的数据库结构
CREATE DATABASE IF NOT EXISTS `shiro_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `shiro_test`;

-- 导出  表 shiro_test.permission 结构
CREATE TABLE IF NOT EXISTS `permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  shiro_test.permission 的数据：~4 rows (大约)
DELETE FROM `permission`;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`pid`, `rname`, `url`) VALUES
	(1, 'add', ''),
	(2, 'delete', ''),
	(3, 'edit', ''),
	(4, 'query', '');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

-- 导出  表 shiro_test.permission_role 结构
CREATE TABLE IF NOT EXISTS `permission_role` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `idx_rid` (`rid`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  shiro_test.permission_role 的数据：~6 rows (大约)
DELETE FROM `permission_role`;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
INSERT INTO `permission_role` (`rid`, `pid`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(2, 1),
	(2, 4);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;

-- 导出  表 shiro_test.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  shiro_test.role 的数据：~2 rows (大约)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`rid`, `name`) VALUES
	(1, 'admin'),
	(2, 'customer');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- 导出  表 shiro_test.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  shiro_test.user 的数据：~2 rows (大约)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`uid`, `username`, `password`) VALUES
	(1, 'admin', '123'),
	(2, 'demo', '123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 导出  表 shiro_test.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  KEY `idx_uid` (`uid`),
  KEY `idx_rid` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  shiro_test.user_role 的数据：~2 rows (大约)
DELETE FROM `user_role`;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`uid`, `rid`) VALUES
	(1, 1),
	(2, 2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
