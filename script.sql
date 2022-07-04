CREATE SCHEMA IF NOT EXISTS `final_project_cafe` DEFAULT CHARACTER SET utf8 ;
USE `final_project_cafe` ;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`users` (
`id_user` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`surname` VARCHAR(45) NOT NULL,
`password` VARCHAR(45) NOT NULL,
`login` VARCHAR(45) NOT NULL,
`email` VARCHAR(45) NOT NULL,
`registration_time` DATETIME NOT NULL,
`phone_number` VARCHAR(45) NOT NULL,
`role` ENUM('administrator', 'client') NOT NULL,
`birthday` DATETIME NOT NULL,
`photo` TEXT NULL,
PRIMARY KEY (`id_user`, `name`, `surname`, `password`, `login`, `email`, `registration_time`, `phone_number`, `role`, `birthday`),
UNIQUE INDEX `id_user_UNIQUE` (`id_user` ASC) VISIBLE,
UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`clients` (
`id_client` INT NOT NULL AUTO_INCREMENT,
`is_block` TINYINT NOT NULL DEFAULT '0',
`loyalty_points` INT NOT NULL DEFAULT '0',
`id_user` INT NOT NULL,
`client_account` DECIMAL(10,0) NOT NULL DEFAULT '0',
PRIMARY KEY (`id_client`, `is_block`, `loyalty_points`, `client_account`),
UNIQUE INDEX `id_client_UNIQUE` (`id_client` ASC) VISIBLE,
INDEX `userClient_idx` (`id_user` ASC) VISIBLE,
CONSTRAINT `userClient`
FOREIGN KEY (`id_user`)
REFERENCES `final_project_cafe`.`users` (`id_user`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`administrators` (
`id_administrator` INT NOT NULL AUTO_INCREMENT,
`experience` DOUBLE NOT NULL DEFAULT 0,
`status` ENUM('declined', 'waiting', 'accepted') NOT NULL DEFAULT 'waiting',
`id_user` INT NOT NULL,
PRIMARY KEY (`id_administrator`, `experience`, `status`),
UNIQUE INDEX `id_administrator_UNIQUE` (`id_administrator` ASC) VISIBLE,
INDEX `userAdmin_idx` (`id_user` ASC) VISIBLE,
CONSTRAINT `userAdmin`
FOREIGN KEY (`id_user`)
REFERENCES `final_project_cafe`.`users` (`id_user`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`orders` (
`id_order` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`date` DATE NOT NULL,
`id_client` INT NOT NULL,
`price` DOUBLE NOT NULL DEFAULT 0,
`payment_type` ENUM('client_account', 'cash') NOT NULL,
PRIMARY KEY (`id_order`, `payment_type`, `price`, `date`, `name`),
UNIQUE INDEX `id_order_UNIQUE` (`id_order` ASC) VISIBLE,
UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
INDEX `orderClient_idx` (`id_client` ASC) VISIBLE,
CONSTRAINT `orderClient`
FOREIGN KEY (`id_client`)
REFERENCES `final_project_cafe2`.`clients` (`id_client`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`products` (
`id_product` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`date` DATE NOT NULL DEFAULT '0000-00-00',
`photo` VARCHAR(50) NULL DEFAULT NULL,
`price` DECIMAL(5,2) NOT NULL DEFAULT 0,
PRIMARY KEY (`id_product`, `name`, `date`, `price`),
UNIQUE INDEX `id_product_UNIQUE` (`id_product` ASC) VISIBLE,
UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`orders_products` (
`id_order` INT NOT NULL,
`id_product` INT NOT NULL,
PRIMARY KEY (`id_order`, `id_product`),
INDEX `listProduct_idx` (`id_product` ASC) VISIBLE,
CONSTRAINT `listOrder`
FOREIGN KEY (`id_order`)
REFERENCES `final_project_cafe`.`orders` (`id_order`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `listProduct`
FOREIGN KEY (`id_product`)
REFERENCES `final_project_cafe`.`products` (`id_product`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`ingredients` (
`id_ingredient` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`shelf_life` DATETIME NOT NULL,
`block` TINYINT NOT NULL DEFAULT 0,
PRIMARY KEY (`id_ingredient`, `name`, `shelf_life`, `block`),
UNIQUE INDEX `id_ingredient_UNIQUE` (`id_ingredient` ASC) VISIBLE,
UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `final_project_cafe`.`products_ingredients` (
`id_ingredient` INT NOT NULL,
`grams` DOUBLE NOT NULL DEFAULT 0,
`id_product` INT NOT NULL,
PRIMARY KEY (`id_ingredient`, `grams`, `id_product`),
INDEX `product_idx` (`id_product` ASC) VISIBLE,
CONSTRAINT `ingredient`
FOREIGN KEY (`id_ingredient`)
REFERENCES `final_project_cafe`.`ingredients` (`id_ingredient`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `product`
FOREIGN KEY (`id_product`)
REFERENCES `final_project_cafe`.`products` (`id_product`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;