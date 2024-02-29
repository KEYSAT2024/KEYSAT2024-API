CREATE TABLE `user_roles` (
  `role_id` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `fk_authorities_user` (`username`),
  CONSTRAINT `fk_authorities_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
SELECT * FROM auth.user;