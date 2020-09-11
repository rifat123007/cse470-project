-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema MyMusic
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MyMusic
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MyMusic` DEFAULT CHARACTER SET utf8 ;
USE `MyMusic` ;

-- -----------------------------------------------------
-- Table `MyMusic`.`Album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Album` (
  `album_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `imagePath` VARCHAR(100) NULL DEFAULT NULL,
  `genre` VARCHAR(45) NULL DEFAULT NULL,
  `year` INT(11) NULL DEFAULT NULL,
  `rating` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`album_id`),
  UNIQUE INDEX `album_id_UNIQUE` (`album_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Artist` (
  `artist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `imagePath` VARCHAR(100) NULL DEFAULT NULL,
  `rating` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`artist_id`),
  UNIQUE INDEX `artist_id_UNIQUE` (`artist_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Album_has_Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Album_has_Artist` (
  `album_id` INT(11) NOT NULL,
  `artist_id` INT(11) NOT NULL,
  PRIMARY KEY (`album_id`, `artist_id`),
  INDEX `fk_Album_has_Artist_Artist1_idx` (`artist_id` ASC),
  CONSTRAINT `fk_Album_has_Artist_Album1`
    FOREIGN KEY (`album_id`)
    REFERENCES `MyMusic`.`Album` (`album_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Album_has_Artist_Artist1`
    FOREIGN KEY (`artist_id`)
    REFERENCES `MyMusic`.`Artist` (`artist_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Track` (
  `track_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `genre` VARCHAR(45) NULL DEFAULT NULL,
  `plays` INT(11) NULL DEFAULT '0',
  `time` VARCHAR(10) NULL DEFAULT NULL,
  `mediaPath` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`track_id`),
  UNIQUE INDEX `track_id_UNIQUE` (`track_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Album_has_Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Album_has_Track` (
  `album_id` INT(11) NOT NULL,
  `track_id` INT(11) NOT NULL,
  PRIMARY KEY (`album_id`, `track_id`),
  INDEX `fk_Album_has_Track_Track1_idx` (`track_id` ASC),
  CONSTRAINT `fk_Album_has_Track_Album1`
    FOREIGN KEY (`album_id`)
    REFERENCES `MyMusic`.`Album` (`album_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Album_has_Track_Track1`
    FOREIGN KEY (`track_id`)
    REFERENCES `MyMusic`.`Track` (`track_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`User` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `admin` TINYINT(1) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Playlist` (
  `playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`),
  UNIQUE INDEX `playlist_id_UNIQUE` (`playlist_id` ASC),
  INDEX `fk_Playlist_User1_idx` (`user_id` ASC),
  CONSTRAINT `fk_Playlist_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `MyMusic`.`User` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Playlist_has_Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Playlist_has_Track` (
  `playlist_id` INT(11) NOT NULL,
  `track_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`, `track_id`),
  INDEX `fk_Playlist_has_Track_Track1_idx` (`track_id` ASC),
  CONSTRAINT `fk_Playlist_has_Track_Playlist1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `MyMusic`.`Playlist` (`playlist_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Playlist_has_Track_Track1`
    FOREIGN KEY (`track_id`)
    REFERENCES `MyMusic`.`Track` (`track_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`Track_has_Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`Track_has_Artist` (
  `track_id` INT(11) NOT NULL,
  `artist_id` INT(11) NOT NULL,
  PRIMARY KEY (`track_id`, `artist_id`),
  INDEX `fk_Track_has_Artist_Artist1_idx` (`artist_id` ASC),
  CONSTRAINT `fk_Track_has_Artist_Artist1`
    FOREIGN KEY (`artist_id`)
    REFERENCES `MyMusic`.`Artist` (`artist_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Track_has_Artist_Track1`
    FOREIGN KEY (`track_id`)
    REFERENCES `MyMusic`.`Track` (`track_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`User_has_Album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`User_has_Album` (
  `user_id` INT(11) NOT NULL,
  `album_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `album_id`),
  INDEX `fk_User_has_Album_Album1_idx` (`album_id` ASC),
  CONSTRAINT `fk_User_has_Album_Album1`
    FOREIGN KEY (`album_id`)
    REFERENCES `MyMusic`.`Album` (`album_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_User_has_Album_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `MyMusic`.`User` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`User_has_Artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`User_has_Artist` (
  `user_id` INT(11) NOT NULL,
  `artist_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `artist_id`),
  INDEX `fk_User_has_Artist_Artist1_idx` (`artist_id` ASC),
  CONSTRAINT `fk_User_has_Artist_Artist1`
    FOREIGN KEY (`artist_id`)
    REFERENCES `MyMusic`.`Artist` (`artist_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_User_has_Artist_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `MyMusic`.`User` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MyMusic`.`User_has_Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyMusic`.`User_has_Track` (
  `track_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`track_id`, `user_id`),
  INDEX `fk_User_has_Track_User1_idx` (`user_id` ASC),
  CONSTRAINT `fk_User_has_Track_Track1`
    FOREIGN KEY (`track_id`)
    REFERENCES `MyMusic`.`Track` (`track_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_User_has_Track_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `MyMusic`.`User` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
