CREATE TABLE `user_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `removed` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Password: EazyBytes@12345
INSERT  INTO `user_account` (`customer_id`, `email`, `pwd`, `removed`) VALUES (1, 'happy@example.com', '{noop}EazyBytes@12345', 0);

-- Password: 987
INSERT  INTO `user_account` (`customer_id`, `email`, `pwd`, `removed`) VALUES (2, 'admin@example.com', '{bcrypt}$2a$12$cfXEFJI/C4YSGDuj8r6KnOeKHTd82W022S8lHTZFgRBqSvtahKVGm', 0);

-- Password: 123
INSERT  INTO `user_account` (`customer_id`, `email`, `pwd`, `removed`) VALUES (1, 'tf', '{bcrypt}$2a$10$.EGF/9QGp2eXZT/ZX4hdm.PtRZyn19DucE4AKChjUHP/U/J43YB2y', 0);


CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
);

INSERT INTO `customer` (`name`,`email`,`mobile_number`, `create_dt`)
 VALUES ('Home','home@example.com','5334122365', CURDATE());


CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_account_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`user_account_id`),
  CONSTRAINT `authorities_user_account_fk` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
);

INSERT INTO `authorities` (`user_account_id`, `name`)
  VALUES (1, 'CUSTOMERACTIONS');

INSERT INTO `authorities` (`user_account_id`, `name`)
  VALUES (1, 'USERACCOUNTACTIONS');

DELETE FROM `authorities`;

INSERT INTO `authorities` (`user_account_id`, `name`)
  VALUES (1, 'ROLE_USER');

INSERT INTO `authorities` (`user_account_id`, `name`)
  VALUES (1, 'ROLE_ADMIN');