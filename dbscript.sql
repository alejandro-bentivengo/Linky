SET @@global.time_zone = '+00:00';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_pk` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`user_pk`),
  UNIQUE KEY `user_pk_UNIQUE` (`user_pk`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `url`;
CREATE TABLE `url` (
  `url_pk` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `user_pk` int(11) NOT NULL,
  `url` varchar(1000) NOT NULL,
  `secure` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`url_pk`),
  UNIQUE KEY `url_pk_UNIQUE` (`url_pk`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `fk_url_user_idx` (`user_pk`),
  CONSTRAINT `fk_url_user` FOREIGN KEY (`user_pk`) REFERENCES `user` (`user_pk`)
) ENGINE=InnoDB;

