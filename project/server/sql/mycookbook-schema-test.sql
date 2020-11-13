-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Database mycookbook_test
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `mycookbook_test`;
CREATE DATABASE IF NOT EXISTS `mycookbook_test` DEFAULT CHARACTER SET utf8 ;
use `mycookbook_test`;



-- -----------------------------------------------------
-- Table `mycookbook_test`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`user_role` (
  `user_role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`user` (
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
    REFERENCES `mycookbook_test`.`user_role` (`user_role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`login` (
  `user_id` INT NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_user_login`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook_test`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(95) NOT NULL,
  `prep_time` INT NOT NULL,
  `cook_time` INT NOT NULL,
  `servings` INT NOT NULL,
  `date` DATE NOT NULL,
  `was_updated` TINYINT(1) NOT NULL,
  `is_featured` TINYINT(1) NOT NULL,
  `calories` INT NULL,
  `image_link` VARCHAR(1022) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`recipe_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_name_UNIQUE` (`name` ASC, `user_id` ASC) VISIBLE,
  CONSTRAINT `FK_user_recipe`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook_test`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`cookbook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`cookbook` (
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
    REFERENCES `mycookbook_test`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`cookbook_recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`cookbook_recipe` (
  `cookbook_recipe_id` INT NOT NULL AUTO_INCREMENT,
  `cookbook_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`cookbook_recipe_id`),
  INDEX `cookbook_id_idx` (`cookbook_id` ASC) VISIBLE,
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `FK_cookbook_cookbook_recipe`
    FOREIGN KEY (`cookbook_id`)
    REFERENCES `mycookbook_test`.`cookbook` (`cookbook_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_recipe_cookbook_recipe`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook_test`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `rating` INT NOT NULL,
  `comment` VARCHAR(45) NULL,
  `date` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`review_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `recipe_id_idx` (`recipe_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_recipe_id_UNIQUE` (`user_id` ASC, `recipe_id` ASC) VISIBLE,
  CONSTRAINT `FK_user_review`
    FOREIGN KEY (`user_id`)
    REFERENCES `mycookbook_test`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_recipe_review`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook_test`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`recipe_tag_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`recipe_tag_category` (
  `recipe_tag_category_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`recipe_tag_category_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`recipe_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`recipe_tag` (
  `recipe_tag_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image_link` VARCHAR(1022) NOT NULL,
  `recipe_tag_category_id` INT NULL,
  PRIMARY KEY (`recipe_tag_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `recipe_tag_category_id_idx` (`recipe_tag_category_id` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_tag_category_recipe_tag`
    FOREIGN KEY (`recipe_tag_category_id`)
    REFERENCES `mycookbook_test`.`recipe_tag_category` (`recipe_tag_category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`recipe_recipe_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`recipe_recipe_tag` (
  `recipe_recipe_tag_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `recipe_tag_id` INT NOT NULL,
  PRIMARY KEY (`recipe_recipe_tag_id`),
  INDEX `recipe_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `recipe_tag_id_idx` (`recipe_tag_id` ASC) VISIBLE,
  UNIQUE INDEX `recipe_id_recipe_tag_id_UNIQUE` (`recipe_id` ASC, `recipe_tag_id` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_recipe_tag`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook_test`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_recipe_tag_recipe_recipe_tag`
    FOREIGN KEY (`recipe_tag_id`)
    REFERENCES `mycookbook_test`.`recipe_tag` (`recipe_tag_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`ingredient` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ingredient_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`measurement_unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`measurement_unit` (
  `measurement_unit_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`measurement_unit_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`recipe_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`recipe_ingredient` (
  `recipe_ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_list_index` INT NOT NULL,
  `quantity` VARCHAR(45) NULL,
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
    REFERENCES `mycookbook_test`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ingredient_recipe_ingredient`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `mycookbook_test`.`ingredient` (`ingredient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_measurement_unit_recipe_ingredient`
    FOREIGN KEY (`measurement_unit_id`)
    REFERENCES `mycookbook_test`.`measurement_unit` (`measurement_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook_test`.`direction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook_test`.`direction` (
  `direction_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `direction_number` INT NOT NULL,
  `text` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`direction_id`),
  UNIQUE INDEX `recipe_id_step_number` (`recipe_id` ASC, `direction_number` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_direction`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook_test`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



delimiter //
create procedure set_known_good_state()
begin

    delete from login;
    delete from review;
	delete from cookbook_recipe;
    delete from cookbook;
    delete from direction;
    delete from recipe_recipe_tag;
    delete from recipe_tag;
    delete from recipe_tag_category;
    delete from recipe_ingredient;
    delete from ingredient;
    delete from measurement_unit;
    delete from recipe;
    delete from `user`;
    delete from user_role;
    
    

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
		(3, 'Noah', 'Mitchelson', 'noah@mitchelson.com', 2),
		(4, 'Irina', 'Cudo', 'irina@cuod.com', 1);
		
	insert into login
		(user_id, user_name, password_hash)
	values 
		(1, 'john.traudt', 'password'),
		(2, 'devon.burwell', 'password'),
		(3, 'noah.mitchelson', 'password'),
		(4, 'irina.cudo', 'password');
		
	insert into recipe
		(recipe_id, `name`, prep_time, cook_time, servings, `date`, was_updated, is_featured, calories, user_id, image_link)
	values
		(1, 'chick\'n', 25, 20, 4, '2020-10-31', 0, 1, null, 1, 'https://ih1.redbubble.net/image.362317170.4069/st,small,507x507-pad,600x600,f8f8f8.jpg'),
		(2, 'mashed potatos', 25, 55, 5, '2020-09-21', 1, 1, 1000, 3, 'https://pbs.twimg.com/profile_images/1322780097452146688/-VTzV1Xa.jpg'),
		(3, 'garden salad', 15, 0, 2, '2020-11-03', 0, 1, 200, 3, 'https://friendlystock.com/wp-content/uploads/2019/06/6-cute-dinosaur-presenting-cartoon-clipart.jpg'),
		(4, 'test recipe', 2, 0, 1, '2020-09-10', 0, 1, null, 3, 'https://pbs.twimg.com/media/EmiZojrW4AAx8We.jpg');
		
	insert into review
		(review_id, rating, `comment`, `date`, user_id, recipe_id)
	values
		(1, 5, 'Very nice!', '2020-11-01', 1, 1),
		(2, 4, 'Pretty good.', '2020-11-11', 1, 2),
		(3, 3, 'Just okay.', '2020-11-05', 1, 3),
		(4, 3, null, '2020-11-09', 3, 1),
		(5, 4, null, '2020-11-09', 3, 2);
		
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
		
	insert into direction
		(direction_id, recipe_id, direction_number, `text`)
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
		(3, 'COOKIN_METHOD'),
		(4, 'TEST_CATEGORY');

	insert into recipe_tag
		(recipe_tag_id, `name`, recipe_tag_category_id, image_link)
	values
		(1, 'CHICKEN', null, 'https://pbs.twimg.com/media/Empjp0BXIAkTCvA?format=jpg&name=large'),
		(2, 'HEARTY', null, 'https://64.media.tumblr.com/4d2f6d0c3e29990123bda22166fe8e86/1220ce887d7a24a9-52/s1280x1920/1642bbc597a807ba47ec68450284de3a1725fb87.png'),
		(3, 'SALAD', 2, 'https://pbs.twimg.com/media/Emom_kfXEAAE6on?format=jpg&name=large'),
		(4, 'AMERICAN', 1, 'https://ih1.redbubble.net/image.1031152073.5216/flat,750x1000,075,f.jpg'),
		(5, 'COLD', null, 'https://cdn.drawception.com/drawings/95033/qpjM9xr2aw.png'),
		(6, 'NO_COOKING_REQUIRED', 3, 'https://media.tenor.com/images/125366f064f15bb3b1bcc91fbb3bc83f/tenor.png'),
		(7, 'OVEN_BAKED', 3, 'https://www.mommyhighfive.com/wp-content/uploads/2020/05/Disney-Toy-Story-Rex-Dinosaur-e1592020175282.jpg'),
		(8, 'TEST_TAG', null, 'https://www.safetysupplywarehouse.com/v/vspfiles/photos/10326-2.jpg');
		
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
		(3, 'OUNCE'),
		(4, 'TEST_UNIT');
		
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
		(11, 'roma tomato'),
		(12, 'test ingredient');
		
	insert into recipe_ingredient
		(recipe_ingredient_id, ingredient_list_index, quantity, recipe_id, ingredient_id, measurement_unit_id)
	values
		(1, 1, '1', 1, 1, null),
		(2, 1, '8', 2, 2, null),
		(3, 2, '1/2', 2, 3, 1),
		(4, 3, null, 2, 4, null),
		(5, 4, null, 2, 5, null),
		(6, 5, '2', 2, 6, 1),
		(7, 6, '1/2', 2, 7, 1),
		(8, 1, '1/2', 3, 1, 2),
		(9, 2, '8', 3, 7, 3),
		(10, 3, '2', 3, 8, null),
		(11, 4, '1', 3, 9, 1),
		(12, 5, '1/2', 3, 10, null),
		(13, 6, '1', 3, 11, null);
end//
delimiter ;


    
    
