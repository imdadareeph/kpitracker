CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `finished` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_role_id` (`role_id`),
  CONSTRAINT `id_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `user_task` (
	`id` int(11) not NULL AUTO_INCREMENT,
  `user_id` int(11)  NOT NULL,
  `task_id` int(11)  NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usertask_user_id` (`user_id`),
  KEY `id_usertask_task_id` (`task_id`),
  CONSTRAINT `id_usertask_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_usertask_task_id` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES (1,'ADMIN');
INSERT INTO `role` VALUES (2,'USER');

INSERT INTO `user` VALUES (8,1,'imdadareeph@gmail.com','Imdad','$2a$10$DMgd3SKvldhp/a3vjOo/WuxEED6NBfKujLX8ef6vUoZH6pp56I04O',1),(9,1,'mohdfaizcs059@gmail.com','Faiz','$2a$10$TDd42umbjoc8V9d/qPYaVuJqpT.9i6Hpwz//O/IIJ18Jgzpxh4zZe',2),(10,1,'abhi.mca50@gmail.com','Abhijeet','$2a$10$HOXI6wPC3W8qB6jZWB2CPeMOwKo7gu7AhYrL6BsJyrfGDBPZgvCEO',2);

