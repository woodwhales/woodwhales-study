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


-- 导出 spring-tx 的数据库结构
CREATE DATABASE IF NOT EXISTS `spring-tx` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `spring-tx`;

-- 导出  表 spring-tx.account 结构
CREATE TABLE IF NOT EXISTS `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL DEFAULT '0',
  `balance` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  spring-tx.account 的数据：~1 rows (大约)
DELETE FROM `account`;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`id`, `username`, `balance`) VALUES
	(1, 'AA', 160);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;

-- 导出  表 spring-tx.book 结构
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(150) NOT NULL DEFAULT '0',
  `book_name` varchar(150) NOT NULL DEFAULT '0',
  `price` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  spring-tx.book 的数据：~2 rows (大约)
DELETE FROM `book`;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`id`, `isbn`, `book_name`, `price`) VALUES
	(1, '1001', 'Java开发指南', 34),
	(2, '1002', 'MySql指南', 56);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- 导出  表 spring-tx.book_stock 结构
CREATE TABLE IF NOT EXISTS `book_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(50) NOT NULL DEFAULT '0',
  `stock` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  spring-tx.book_stock 的数据：~2 rows (大约)
DELETE FROM `book_stock`;
/*!40000 ALTER TABLE `book_stock` DISABLE KEYS */;
INSERT INTO `book_stock` (`id`, `isbn`, `stock`) VALUES
	(1, '1001', 4),
	(2, '1002', 8);
/*!40000 ALTER TABLE `book_stock` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
