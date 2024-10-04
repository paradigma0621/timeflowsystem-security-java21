CREATE TABLE `user_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  `removed` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Password: EazyBytes@12345
INSERT  INTO `user_account` (`customer_id`, `email`, `pwd`, `role`, `removed`) VALUES (1, 'happy@example.com', '{noop}EazyBytes@12345', 'read', 0);

-- Password: 987
INSERT  INTO `user_account` (`customer_id`, `email`, `pwd`, `role`, `removed`) VALUES (2, 'admin@example.com', '{bcrypt}$2a$12$cfXEFJI/C4YSGDuj8r6KnOeKHTd82W022S8lHTZFgRBqSvtahKVGm', 'admin', 0);