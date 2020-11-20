-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Database mycookbook
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `mycookbook`;
CREATE DATABASE IF NOT EXISTS `mycookbook` DEFAULT CHARACTER SET utf8 ;
use `mycookbook`;

-- -----------------------------------------------------
-- Table `mycookbook`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`user_role` (
  `user_role_id` INT NOT NULL AUTO_INCREMENT,
  `user_role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE INDEX `user_role_name_UNIQUE` (`user_role_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
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
  `password_hash` VARCHAR(255) NOT NULL,
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
  `recipe_name` VARCHAR(255) NOT NULL,
  `prep_time` INT NOT NULL,
  `cook_time` INT NOT NULL,
  `servings` INT NOT NULL,
  `date` DATE NOT NULL,
  `was_updated` TINYINT(1) NOT NULL,
  `is_featured` TINYINT(1) NOT NULL,
  `calories` INT NULL,
  `image_link` VARCHAR(2046) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`recipe_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_recipe_name_UNIQUE` (`recipe_name` ASC, `user_id` ASC) VISIBLE,
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
  `title` VARCHAR(255) NOT NULL,
  `is_private` TINYINT(1) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`cookbook_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `cookbook_id_UNIQUE` (`cookbook_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_title_UNIQUE` (`user_id` ASC, `title` ASC) VISIBLE,
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
  `comment` VARCHAR(2046) NULL,
  `review_date` DATE NOT NULL,
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
  `recipe_tag_category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`recipe_tag_category_id`),
  UNIQUE INDEX `recipe_tag_category_name_UNIQUE` (`recipe_tag_category_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe_tag` (
  `recipe_tag_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_tag_name` VARCHAR(255) NOT NULL,
  `tag_image_link` VARCHAR(2046) NOT NULL,
  `recipe_tag_category_id` INT NULL,
  PRIMARY KEY (`recipe_tag_id`),
  UNIQUE INDEX `recipe_tag_name_UNIQUE` (`recipe_tag_name` ASC) VISIBLE,
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
  `ingredient_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ingredient_id`),
  UNIQUE INDEX `ingredient_name_UNIQUE` (`ingredient_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`measurement_unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`measurement_unit` (
  `measurement_unit_id` INT NOT NULL AUTO_INCREMENT,
  `measurement_unit_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`measurement_unit_id`),
  UNIQUE INDEX `measurement_unit_name_UNIQUE` (`measurement_unit_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mycookbook`.`recipe_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`recipe_ingredient` (
  `recipe_ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_list_index` INT NOT NULL,
  `quantity` VARCHAR(255) NULL,
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
-- Table `mycookbook`.`direction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mycookbook`.`direction` (
  `direction_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `direction_number` INT NOT NULL,
  `direction_text` VARCHAR(2046) NOT NULL,
  PRIMARY KEY (`direction_id`),
  UNIQUE INDEX `recipe_id_direction_number` (`recipe_id` ASC, `direction_number` ASC) VISIBLE,
  CONSTRAINT `FK_recipe_direction`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `mycookbook`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



insert into user_role 
	(user_role_id, user_role_name) 
values
	(1, 'USER'),
    (2, 'MODERATOR');
    
insert into `user`
	(user_id, first_name, last_name, email, user_role_id)
values
	(1, 'John', 'Traudt', 'john@traudt.com', 1),
    (2, 'Devon', 'Burwell', 'devon@burwell.com', 1),
    (3, 'Noah', 'Mitchelson', 'noah@mitchelson.com', 2);
    
insert into login
	(user_id, user_name, password_hash)
values 
	(1, 'john.traudt', '$2a$10$9Yijg1jDZdENEQFz9jZBxO7CvHsRuz0L2s2X.3/tKKG3FFKwsTR9q'),
    (2, 'devon.burwell', '$2a$10$9Yijg1jDZdENEQFz9jZBxO7CvHsRuz0L2s2X.3/tKKG3FFKwsTR9q'),
    (3, 'noah.mitchelson', '$2a$10$9Yijg1jDZdENEQFz9jZBxO7CvHsRuz0L2s2X.3/tKKG3FFKwsTR9q');
    
-- insert  into recipe
-- 	(recipe_id, recipe_name, prep_time, cook_time, servings, `date`, was_updated, is_featured, calories, user_id, image_link)
-- values
-- 	(1, 'chick\'n', 25, 20, 4, '2020-10-31', 0, 1, null, 1, 'https://ih1.redbubble.net/image.362317170.4069/st,small,507x507-pad,600x600,f8f8f8.jpg'),
--     (2, 'mashed potatos', 25, 55, 5, '2020-09-21', 1, 1, 1000, 3, 'https://pbs.twimg.com/profile_images/1322780097452146688/-VTzV1Xa.jpg'),
--     (3, 'garden salad', 15, 0, 2, '2020-11-03', 0, 1, 200, 3, 'https://friendlystock.com/wp-content/uploads/2019/06/6-cute-dinosaur-presenting-cartoon-clipart.jpg');
    
-- insert into review
-- 	(review_id, rating, `comment`, review_date, user_id, recipe_id)
-- values
-- 	(1, 5, 'Very nice!', '2020-11-01', 1, 1),
--     (2, 4, 'Pretty good.', '2020-11-11', 1, 2),
--     (3, 3, 'Just okay.', '2020-11-05', 1, 3),
--     (4, 3, null, '2020-11-09', 3, 1),
--     (5, 4, null, '2020-11-09', 3, 2);
    
-- insert into cookbook
-- 	(cookbook_id, title, is_private, user_id)
-- values
-- 	(1, 'Assorted Recipes', 0, 1),
--     (2, 'A Good Meal', 1, 2);
    
-- insert into cookbook_recipe
-- 	(cookbook_recipe_id, cookbook_id, recipe_id)
-- values
-- 	(1, 1, 1),
--     (2, 1, 2),
--     (3, 1, 3),
--     (4, 2, 2),
--     (5, 2, 3);
    
-- insert into direction
-- 	(direction_id, recipe_id, direction_number, direction_text)
-- values
-- 	(1, 1, 1, 'Buy an entire chicken'),
--     (2, 1, 2, 'Bake the entire chicken for 20 minutes'),
--     (3, 1, 3, 'Cut the entire chicken up'),
--     (4, 2, 1, 'Peel 8 potatos'),
--     (5, 2, 2, 'Cut the potatos into chunks'),
--     (6, 2, 3, 'Boil the potatos until soft'),
--     (7, 2, 4, 'Mash em with a fork in a large bowl'),
--     (8, 2, 5, 'Add 1 cup butter, 2 cups shredded cheese, and 1/2 cup bacon bits. Mix well'),
--     (9, 2, 6, 'Add salt and pepper to taste'),
--     (10, 3, 1, 'Finely chop iceberg lettuce and place in a large bowl with 1 cup of carrot slices, 1/2 lb of chicken, 8 oz shredded cheese, 1/2 diced red onion, and 1 diced roma tomato'),
--     (11, 3, 2, 'Toss that salad!'),
--     (12, 3, 3, 'Add your preferred salad dressing to taste');
    
insert into recipe_tag_category
	(recipe_tag_category_id, recipe_tag_category_name)
values
	(1, 'ETHNICITY'),
    (2, 'MEAL_TYPE'),
    (3, 'COOKIN_METHOD');

insert into recipe_tag
	(recipe_tag_id, recipe_tag_name, recipe_tag_category_id, tag_image_link)
values
	(1, 'CHICKEN', null, 'https://www.simplyhappyfoodie.com/wp-content/uploads/2019/07/air-fryer-chicken-breasts-1b-500x500.jpg'),
    (2, 'HEARTY', null, 'https://media3.s-nbcnews.com/j/newscms/2019_05/2732486/190128-damn-delicious_quinoa-chili_1-ac-517p_b0050019381ded2f8355ab9b67fec347.fit-760w.jpg'),
    (3, 'SALAD', 2, 'https://www.wholesomeyum.com/wp-content/uploads/2020/03/wholesomeyum-chef-salad-recipe-4.jpg'),
    (4, 'AMERICAN', 1, 'https://idc.edu/wp-content/uploads/2018/07/Traditional-American-Food-You-Must-Try-While-Studying-in-Washington-DC-850x390.jpg'),
    (5, 'COLD', null, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQTQy53Pv60KrguaRHINwtZug5Nf--oU0Drg&usqp=CAU'),
    (6, 'NO_COOKING_REQUIRED', 3, 'https://www.bostonmagazine.com/wp-content/uploads/sites/2/2019/06/no-cook-meals.jpg'),
    (7, 'OVEN_BAKED', 3, 'https://ajmadison.brightspotcdn.com/cd/7f/5ff3ef414f07b3d45b8b3a06f627/pod301rw-4.jpg'),
    (8, 'BEEF', 2, 'https://www.budgetbytes.com/wp-content/uploads/2018/06/Beef-Kofta-Meatballs-with-Roasted-Vegetables-finished.jpg'),
    (9, 'VEGETARIAN', 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2PAN8l_u6YSjmGbBxc0uPdGO53prd_THVgw&usqp=CAU'),
    (10, 'SOUP', 2, 'https://www.eatwell101.com/wp-content/uploads/2020/02/chicken-soup-recipe-3.jpg'),
    (11, 'DESSERT', 2, 'https://www.jocooks.com/wp-content/uploads/2012/03/sex-in-a-pan-1-4-1-500x500.jpg'),
    (12, 'BREAD', null, 'https://s23991.pcdn.co/wp-content/uploads/2016/01/five-minute-artisan-bread-recipe.jpg'),
    (13, 'BREAKFAST', 2, 'https://simply-delicious-food.com/wp-content/uploads/2018/10/breakfast-board-500x500.jpg'),
    (14, 'LUNCH', 2, 'https://images.immediate.co.uk/production/volatile/sites/30/2020/08/boxing-day-banh-mi-10836a8.jpg?quality=90&resize=960,872'),
    (15, 'DINNER', 2, 'https://media3.s-nbcnews.com/i/newscms/2019_05/2736521/190131-stock-taco-bar-food-ew-1220p_bc7c9fc25ecd393bfa3d7d35f216edfc.jpg');
    
-- insert into recipe_recipe_tag
-- 	(recipe_recipe_tag_id, recipe_id, recipe_tag_id)
-- values
-- 	(1, 1, 1),
--     (2, 1, 7),
--     (3, 2, 2),
--     (4, 2, 4),
--     (5, 3, 1),
--     (6, 3, 3),
--     (7, 3, 5),
--     (8, 3, 6);
    
insert into measurement_unit
	(measurement_unit_id, measurement_unit_name)
values
	(1, 'cup'),
    (2, 'lb'),
    (3, 'oz'),
    (4, 'tbsp'),
    (5, '-- --'),
    (6, 'tsp'),
    (7, 'pint'),
    (8, 'qt'),
    (9, 'gal'),
    (10, 'g'),
    (11, 'pinch'),
    (12, 'dash');
    
-- insert into ingredient
-- 	(ingredient_id, ingredient_name)
-- values
-- 	(1, 'chicken'),
--     (2, 'Russet potato'),
--     (3, 'butter'),
--     (4, 'salt'),
--     (5, 'black pepper'),
--     (6, 'shredded cheese'),
--     (7, 'bacon bits'),
--     (8, 'Iceberg lettuce'),
--     (9, 'carrot slices'),
--     (10, 'diced red onion'),
--     (11, 'roma tomato');
    
-- insert into recipe_ingredient
-- 	(recipe_ingredient_id, ingredient_list_index, quantity, recipe_id, ingredient_id, measurement_unit_id)
-- values
-- 	(1, 1, '1', 1, 1, null),
--     (2, 1, '8', 2, 2, null),
--     (3, 2, '1/2', 2, 3, 1),
--     (4, 3, null, 2, 4, null),
--     (5, 4, null, 2, 5, null),
--     (6, 5, '2', 2, 6, 1),
--     (7, 6, '1/2', 2, 7, 1),
--     (8, 1, '1/2', 3, 1, 2),
--     (9, 2, '8', 3, 7, 3),
--     (10, 3, '2', 3, 8, null),
--     (11, 4, '1', 3, 9, 1),
--     (12, 5, '1/2', 3, 10, null),
--     (13, 6, '1', 3, 11, null);
    
    
