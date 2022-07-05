CREATE SCHEMA IF NOT EXISTS `final_project_cafe` DEFAULT CHARACTER SET utf8 ;
USE `final_project_cafe` ;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`users` (
`id_user` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`surname` VARCHAR(45) NOT NULL,
`password` VARCHAR(45) NOT NULL,
`login` VARCHAR(45) NOT NULL,
`email` VARCHAR(45) NOT NULL,
`registration_time` DATETIME NOT NULL,
`phone_number` VARCHAR(45) NOT NULL,
`role` ENUM('administrator', 'client') NOT NULL,
`birthday` DATETIME NOT NULL,
`photo` TEXT NULL)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`clients` (
`id_client` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`is_block` TINYINT NOT NULL DEFAULT '0',
`loyalty_points` INT NOT NULL DEFAULT '0',
`id_user` INT NOT NULL,
`client_account` DECIMAL(10,0) NOT NULL DEFAULT '0')
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`administrators` (
`id_administrator` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`experience` DOUBLE NOT NULL DEFAULT 0,
`status` ENUM('declined', 'waiting', 'accepted') NOT NULL DEFAULT 'waiting',
`id_user` INT NOT NULL)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`orders` (
`id_order` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`date` DATE NOT NULL,
`id_client` INT NOT NULL,
`price` DOUBLE NOT NULL DEFAULT 0,
`payment_type` ENUM('client_account', 'cash') NOT NULL)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`products` (
`id_product` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`date` DATE NOT NULL DEFAULT '0000-00-00',
`photo` VARCHAR(50) NULL DEFAULT NULL,
`price` DECIMAL(5,2) NOT NULL DEFAULT 0)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`orders_products` (
`id_order` INT NOT NULL,
`id_product` INT NOT NULL)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`ingredients` (
`id_ingredient` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`shelf_life` DATETIME NOT NULL,
`block` TINYINT NOT NULL DEFAULT 0)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`products_ingredients` (
`id_ingredient` INT NOT NULL,
`grams` DOUBLE NOT NULL DEFAULT 0,
`id_product` INT NOT NULL)
ENGINE = InnoDB;