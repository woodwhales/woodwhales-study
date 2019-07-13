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


-- 导出 spring-jdbc 的数据库结构
CREATE DATABASE IF NOT EXISTS `spring-jdbc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `spring-jdbc`;

-- 导出  表 spring-jdbc.departments 结构
CREATE TABLE IF NOT EXISTS `departments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  spring-jdbc.departments 的数据：~3 rows (大约)
DELETE FROM `departments`;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` (`id`, `dept_name`) VALUES
	(1, '后端开发'),
	(2, '前端开发'),
	(3, '运营');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;

-- 导出  表 spring-jdbc.employees 结构
CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `dept_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  spring-jdbc.employees 的数据：~11 rows (大约)
DELETE FROM `employees`;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` (`id`, `last_name`, `email`, `dept_id`) VALUES
	(1, '员工1', 'AA@qq.com', 1),
	(2, '员工2', 'BB@qq.com', 2),
	(3, 'FF', 'ff@qq.com', 2),
	(4, 'AA', 'aa@qq.com', 1),
	(5, 'Jack', 'bb@qq.com', 2),
	(6, 'CC', 'cc@qq.com', 3),
	(7, 'DD', 'dd@qq.com', 3),
	(8, 'EE', 'ee@qq.com', 2),
	(9, 'XYZ', 'xyz@qq.com', 3),
	(10, 'FF', 'ff@qq.com', 2),
	(11, 'FF', 'ff@qq.com', 2);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
