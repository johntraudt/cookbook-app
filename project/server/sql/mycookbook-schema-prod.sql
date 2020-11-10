-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mycookbook
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mycookbook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mycookbook` DEFAULT CHARACTER SET utf8 ;
USE `mycookbook` ;

-- -----------------------------------------------------
-- Table `mycookbook`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`user_role` (
  `user_role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `user_role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `user_role_id_idx` (`user_role_id` ASC) VISIBLE,
  CONSTRAINT `FK_user_role_user`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `mycookbook`.`user_role` (`user_role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`login` (
  `user_id` INT NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`password_hash` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_user_login`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `prep_time` INT NOT NULL,
  `cook_time` INT NOT NULL,
  `servings` INT NOT NULL,
  `date` DATE NOT NULL,
  `was_updated` TINYINT(1) NOT NULL,
  `calories` INT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`recipe_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK_user_recipe`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`cookbook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`cookbook` (
  `cookbook_id` INT NOT NULL AUTO_INCREMENT,
  `private` TINYINT(1) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`cookbook_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK_user_cookbook`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`cookbook_recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`cookbook_recipe` (
  `cookbook_recipe_id` INT NOT NULL AUTO_INCREMENT,
  `cookbook_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`cookbook_recipe_id`),
  INDEX `cookbook_id_idx` (`cookbook_id` ASC) VISIBLE,
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `FK_cookbook_cookbook_recipe`
    FOREIGN KEY (`cookbook_id`)
    REFERENCES `mycookbook`.`cookbook` (`cookbook_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_recipe_cookbook_recipe`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `rating` INT NOT NULL,
  `message` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`review_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `FK_user_review`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_recipe_review`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe_tag_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe_tag_category` (
  `recipe_tage_category_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`recipe_tage_category_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe_tag` (
  `recipe_tag_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `recipe_tag_category_id` INT NULL,
  PRIMARY KEY (`recipe_tag_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `recipe_tag_category_id_idx` (`recipe_tag_category_id` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_tag_category_recipe_tag`
    FOREIGN KEY (`recipe_tag_category_id`)
    REFERENCES `mycookbook`.`recipe_tag_category` (`recipe_tage_category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe_recipe_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe_recipe_tag` (
  `recipe_recipe_tag_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `recipe_tag_id` INT NOT NULL,
  PRIMARY KEY (`recipe_recipe_tag_id`),
  INDEX `recipe_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `recipe_tag_id_idx` (`recipe_tag_id` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_recipe_tag`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_recipe_tag_recipe_recipe_tag`
    FOREIGN KEY (`recipe_tag_id`)
    REFERENCES `mycookbook`.`recipe_tag` (`recipe_tag_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`ingredient` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ingredient_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`measurement_unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`measurement_unit` (
  `measurement_unit_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`measurement_unit_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe_ingredient` (
  `recipe_ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `quantity` DECIMAL(5,2) NOT NULL,
  `recipe_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  `measurement_unit_id` INT NULL,
  PRIMARY KEY (`recipe_ingredient_id`),
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `ingredient_id_idx` (`ingredient_id` ASC) VISIBLE,
  INDEX `measurement_unit_id_idx` (`measurement_unit_id` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_recipe_ingredient`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ingredient_recipe_ingredient`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `mycookbook`.`ingredient` (`ingredient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_measurement_unit_recipe_ingredient`
    FOREIGN KEY (`measurement_unit_id`)
    REFERENCES `mycookbook`.`measurement_unit` (`measurement_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`step`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`step` (
  `recipe_id` INT NOT NULL,
  `step_number` INT NOT NULL,
  `text` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`recipe_id`, `step_number`),
  CONSTRAINT `FK_recipe_step`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
