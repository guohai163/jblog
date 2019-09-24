CREATE DATABASE IF NOT EXISTS jblog default character set utf8mb4 collate utf8mb4_unicode_ci;

USE jblog;

CREATE TABLE IF NOT EXISTS `jblog_posts` (
  `post_code` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `post_date` datetime NOT NULL,
  `post_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `post_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `post_status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `post_small_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `jblog_user` (
  `user_code` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) CHARACTER SET 'utf8mb4' COLLATE utf8mb4_general_ci NOT NULL,
  `user_pass` varchar(200) CHARACTER SET 'utf8mb4' COLLATE utf8mb4_general_ci NOT NULL,
  `user_key` varchar(50) CHARACTER SET 'utf8mb4' COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_code`),
  UNIQUE KEY unique_user_name (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/**Init admin**/
INSERT INTO jblog_user (user_name,user_pass,user_key)VALUES('admin','05dc026de2609e935ed1539aedd24238','MH370');

CREATE TABLE IF NOT EXISTS `jblog_hotkey` (
  `hotkey` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci' NOT NULL,
  `hotkey_count` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`hotkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `jblog_class` (
  `class_code` INT NOT NULL AUTO_INCREMENT,
  `class_name` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci' NOT NULL,
  PRIMARY KEY (`class_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `jblog_class_map` (
  `map_code` INT NOT NULL AUTO_INCREMENT,
  `class_code` INT NOT NULL,
  `post_code` INT NOT NULL,
  PRIMARY KEY (`map_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
