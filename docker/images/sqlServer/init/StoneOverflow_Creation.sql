-- MySQL Script generated by MySQL Workbench
-- Fri Oct  2 15:58:35 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_stoneoverflow
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `db_stoneoverflow` ;

-- -----------------------------------------------------
-- Schema db_stoneoverflow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_stoneoverflow` DEFAULT CHARACTER SET utf8 ;
USE `db_stoneoverflow` ;

-- -----------------------------------------------------
-- Table `db_stoneoverflow`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_stoneoverflow`.`User` (
  `id` CHAR(36) NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `mail` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_stoneoverflow`.`UserMessage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_stoneoverflow`.`UserMessage` (
  `id` CHAR(36) NOT NULL,
  `idUser` CHAR(36) NOT NULL,
  `description` TEXT NULL,
  `nbVotes` INT NULL,
  `date` DATETIME(3) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_UserMessage_User1_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `fk_UserMessage_User1`
    FOREIGN KEY (`idUser`)
    REFERENCES `db_stoneoverflow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_stoneoverflow`.`Question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_stoneoverflow`.`Question` (
  `id` CHAR(36) NOT NULL,
  `title` VARCHAR(100) NULL,
  `nbViews` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Question_UserMessage1_idx` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_Question_UserMessage1`
    FOREIGN KEY (`id`)
    REFERENCES `db_stoneoverflow`.`UserMessage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_stoneoverflow`.`Answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_stoneoverflow`.`Answer` (
  `id` CHAR(36) NOT NULL,
  `idQuestion` CHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Answer_Question1_idx` (`idQuestion` ASC) VISIBLE,
  CONSTRAINT `fk_Answer_UserMessage1`
    FOREIGN KEY (`id`)
    REFERENCES `db_stoneoverflow`.`UserMessage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Answer_Question1`
    FOREIGN KEY (`idQuestion`)
    REFERENCES `db_stoneoverflow`.`Question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_stoneoverflow`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_stoneoverflow`.`Comment` (
  `id` CHAR(36) NOT NULL,
  `idUserMessage` VARCHAR(36) NOT NULL,
  `idUser` VARCHAR(36) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Comment_UserMessage1_idx` (`idUserMessage` ASC) VISIBLE,
  INDEX `fk_Comment_User1_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `fk_Comment_UserMessage1`
    FOREIGN KEY (`idUserMessage`)
    REFERENCES `db_stoneoverflow`.`UserMessage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`idUser`)
    REFERENCES `db_stoneoverflow`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
