-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mycookbook
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `mycookbook`;
CREATE DATABASE IF NOT EXISTS `mycookbook` DEFAULT CHARACTER SET utf8 ;
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
  UNIQUE INDEX `user_id_name_UNIQUE` (`name` ASC, `user_id` ASC) VISIBLE,
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
  `name` VARCHAR(45) NOT NULL,
  `is_private` TINYINT(1) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`cookbook_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `cookbook_id_UNIQUE` (`cookbook_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_name_UNIQUE` (`user_id` ASC, `name` ASC) VISIBLE,
  UNIQUE INDEX `cookbook_id_recipe_id_UNIQUE` (`cookbook_id` ASC, `user_id` ASC) VISIBLE,
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
  `comment` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`review_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_recipe_id_UNIQUE` (`user_id` ASC, `recipe_id` ASC) VISIBLE,
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
  `recipe_tag_category_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`recipe_tag_category_id`),
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
    REFERENCES `mycookbook`.`recipe_tag_category` (`recipe_tag_category_id`)
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
  UNIQUE INDEX `recipe_id_recipe_tag_id_UNIQUE` (`recipe_id` ASC, `recipe_tag_id` ASC) VISIBLE,
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
  PRIMARY KEY (`ingredient_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
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
  `ingredient_list_index` INT NOT NULL,
  `quantity` DECIMAL(5,2) NULL,
  `recipe_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  `measurement_unit_id` INT NULL,
  PRIMARY KEY (`recipe_ingredient_id`),
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `ingredient_id_idx` (`ingredient_id` ASC) VISIBLE,
  INDEX `measurement_unit_id_idx` (`measurement_unit_id` ASC) VISIBLE,
  UNIQUE INDEX `recipe_ingredient_id_ingredient_listIndex_UNIQUE` (`recipe_ingredient_id` ASC, `ingredient_list_index` ASC) VISIBLE,
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
  `step_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `step_number` INT NOT NULL,
  `text` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`step_id`),
  UNIQUE INDEX `recipe_id_step_number` (`recipe_id` ASC, `step_number` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_step`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


delimiter //
create procedure set_known_good_state()
begin

end//
delimiter ;

insert into user_role 
	(user_role_id, `name`) 
values
	(1, 'USER'),
    (2, 'MODERATOR');
    
insert into `user`
	(user_id, first_name, last_name, email, user_role_id)
values
	(1, 'John', 'Traudt', 'john@traudt.com', 1),
    (2, 'Devon', 'Burwell', 'devon@burwell.com', 1),
    (3, 'Noah', 'Mitchelson', 'noah@mitchelson.com', 1);
    
insert into login
	(user_id, user_name, password_hash)
values 
	(1, 'john.traudt', 'password'),
    (2, 'devon.burwell', 'password'),
    (3, 'noah.mitchelson', 'password');
    
insert into recipe
	(recipe_id, `name`, prep_time, cook_time, servings, `date`, was_updated, calories, user_id)
values
	(1, 'chick\'n', 25, 20, 4, '2020-10-31', 0, null, 1),
    (2, 'mashed potatos', 25, 55, 5, '2020-09-21', 1, 1000, 3),
    (3, 'garden salad', 15, 0, 2, '2020-11-03', 0, 200, 3);
    
insert into review
	(review_id, rating, `comment`, user_id, recipe_id)
values
	(1, 5, 'Very nice!', 1, 1),
    (2, 4, 'Pretty good.', 1, 2),
    (3, 3, 'Just okay.', 1, 3),
    (4, 3, null, 3, 1),
    (5, 4, null, 3, 2);
    
insert into cookbook
	(cookbook_id, `name`, is_private, user_id)
values
	(1, 'Assorted Recipes', 0, 1),
    (2, 'A Good Meal', 1, 2);
    
insert into cookbook_recipe
	(cookbook_recipe_id, cookbook_id, recipe_id)
values
	(1, 1, 1),
    (2, 1, 2),
    (3, 1, 3),
    (4, 2, 2),
    (5, 2, 3);
    
insert into step
	(step_id, recipe_id, step_number, `text`)
values
	(1, 1, 1, 'Buy an entire chicken'),
    (2, 1, 2, 'Bake the entire chicken for 20 minutes'),
    (3, 1, 3, 'Cut the entire chicken up'),
    (4, 2, 1, 'Peel 8 potatos'),
    (5, 2, 2, 'Cut the potatos into chunks'),
    (6, 2, 3, 'Boil the potatos until soft'),
    (7, 2, 4, 'Mash em with a fork in a large bowl'),
    (8, 2, 5, 'Add 1 cup butter, 2 cups shredded cheese, and 1/2 cup bacon bits. Mix well'),
    (9, 2, 6, 'Add salt and pepper to taste'),
    (10, 3, 1, 'Finely chop iceberg lettuce and place in a large bowl with 1 cup of carrot slices, 1/2 lb of chicken, 8 oz shredded cheese, 1/2 diced red onion, and 1 diced roma tomato'),
    (11, 3, 2, 'Toss that salad!'),
    (12, 3, 3, 'Add your preferred salad dressing to taste');
    
insert into recipe_tag_category
	(recipe_tag_category_id, `name`)
values
	(1, 'ETHNICITY'),
    (2, 'MEAL_TYPE'),
    (3, 'COOKIN_METHOD');

insert into recipe_tag
	(recipe_tag_id, `name`, recipe_tag_category_id)
values
	(1, 'CHICKEN', null),
    (2, 'HEARTY', null),
    (3, 'SALAD', 2),
    (4, 'AMERICAN', 1),
    (5, 'COLD', null),
    (6, 'NO_COOKING_REQUIRED', 3),
    (7, 'OVEN_BAKED', 3);
    
insert into recipe_recipe_tag
	(recipe_recipe_tag_id, recipe_id, recipe_tag_id)
values
	(1, 1, 1),
    (2, 1, 7),
    (3, 2, 2),
    (4, 2, 4),
    (5, 3, 1),
    (6, 3, 3),
    (7, 3, 5),
    (8, 3, 6);
    
insert into measurement_unit
	(measurement_unit_id, `name`)
values
	(1, 'CUP'),
    (2, 'POUND'),
    (3, 'OUNCE');
    
insert into ingredient
	(ingredient_id, `name`)
values
	(1, 'chicken'),
    (2, 'Russet potato'),
    (3, 'butter'),
    (4, 'salt'),
    (5, 'black pepper'),
    (6, 'shredded cheese'),
    (7, 'bacon bits'),
    (8, 'Iceberg lettuce'),
    (9, 'carrot slices'),
    (10, 'diced red onion'),
    (11, 'roma tomato');
    
insert into recipe_ingredient
	(recipe_ingredient_id, ingredient_list_index, quantity, recipe_id, ingredient_id, measurement_unit_id)
values
	(1, 1, 1, 1, 1, null),
    (2, 2, 8, 2, 2, null),
    (3, 3, 0.5, 2, 3, 1),
    (4, 4, null, 2, 4, null),
    (5, 5, null, 2, 5, null),
    (6, 6, 2, 2, 6, 1),
    (7, 7, 0.5, 2, 7, 1),
    (8, 1, 0.5, 3, 1, 2),
    (9, 2, 8, 3, 7, 3),
    (10, 3, 2, 3, 8, null),
    (11, 4, 1, 3, 9, 1),
    (12, 5, 0.5, 3, 10, null),
    (13, 6, 1, 3, 11, null);