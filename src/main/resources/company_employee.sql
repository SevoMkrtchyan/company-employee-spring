/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33-0ubuntu0.16.04.1 : Database - company_employee
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`company_employee` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `company_employee`;

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `size` int(11) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `company` */

insert  into `company`(`id`,`name`,`size`,`address`) values 
(1,'ITSpace',30,'paligonner'),
(2,'VivacellMTC',350,'erevan'),
(6,'no_company',5,'no_company');

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `salary` double(10,2) NOT NULL,
  `position` enum('PRESIDENT','VICE_PRESIDENT','MANAGER','ADMINISTRATOR','OPEN_TO_WORK','NO_POSITION_YET') DEFAULT 'NO_POSITION_YET',
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

insert  into `employee`(`id`,`name`,`surname`,`email`,`username`,`password`,`phone_number`,`salary`,`position`,`company_id`) values 
(1,'Sevak','Mkrtchyan','sevada.mkrtchyan.95@gmail.com','','',94199523,1500000.00,'PRESIDENT',2),
(4,'Narek','Hovhannisyan','hovhannisyan@mail.ru','','',46545,5000000.00,'VICE_PRESIDENT',2),
(5,'Sevak','Mkrtchyan','chka@mail.ru','','',256,5000000.00,'ADMINISTRATOR',1),
(10,'jkhjkhk','jkhjkhk','kjhjk','','',789,465.00,'OPEN_TO_WORK',6),
(11,'klfjljkl','jkljskl','fmckll','','',23,23.00,NULL,6),
(13,'eli taza','dsjk','scsajk','','',3891,893472.00,'NO_POSITION_YET',2),
(14,'hkhkhk','fghfj','fhfh','','',7899,456.00,'NO_POSITION_YET',2),
(15,'login_exnox','cjk','hdkjhk','petros','$2a$10$4xjSO8uvpYIdBqFA/aChFudx1iuS5TkAyFq6UpRRY18RG5HnkTRVu',46549,465.00,'NO_POSITION_YET',2),
(16,'admin','adminyan','admin@mail.ru','admin087','$2a$10$LjyGu9nHdobdhGAnZqqkxewQ253elsGPglYw6FzJlupxhB/vGQnxK',46546,4.00,'ADMINISTRATOR',6),
(17,'poxos','poxosyan','poxos@mail.ru','poxos','$2a$10$d0zKTGZG4Kvw5vpJKR5yKOdcFqNH9eHGYN22KXhznlWuyGMALTmWG',444,555.00,'NO_POSITION_YET',2);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sms` varchar(255) NOT NULL,
  `timestamp` varchar(255) NOT NULL,
  `from_id` int(11) NOT NULL,
  `to_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `from_id` (`from_id`),
  KEY `to_id` (`to_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`to_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `message` */

insert  into `message`(`id`,`sms`,`timestamp`,`from_id`,`to_id`) values 
(1,'dlfkdsl;','Sat Aug 28 23:31:26 AMT 2021',15,1),
(2,'5646','Sat Aug 28 23:33:01 AMT 2021',15,1),
(3,'kljl','Sat Aug 28 23:39:51 AMT 2021',15,4),
(4,'barev dez','Sat Aug 28 23:40:21 AMT 2021',15,4),
(5,'barev','Sun Aug 29 00:43:14 AMT 2021',15,17),
(6,'batev d','Sun Aug 29 00:44:43 AMT 2021',17,15),
(7,'inchxes','Sun Aug 29 00:52:47 AMT 2021',15,17),
(8,'lav du?','Sun Aug 29 00:57:01 AMT 2021',17,15),
(9,'yola grqash','Sun Aug 29 00:57:13 AMT 2021',15,17),
(10,'kkk','Sun Aug 29 01:23:15 AMT 2021',15,17),
(11,'hhh','Sun Aug 29 01:26:22 AMT 2021',15,17),
(12,'@b@','Sun Aug 29 01:26:42 AMT 2021',17,15),
(13,'','Sun Aug 29 01:43:56 AMT 2021',15,15),
(14,'','Sun Aug 29 01:54:30 AMT 2021',15,17),
(15,'','Sun Aug 29 01:54:39 AMT 2021',15,17),
(16,'kljsklc','Sun Aug 29 17:25:50 AMT 2021',15,13);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
