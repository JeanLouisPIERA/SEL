DROP TABLE IF EXISTS users;

CREATE TABLE user (
  idUser bigint(11) AUTO_INCREMENT  PRIMARY KEY,
  username varchar(25) NOT NULL UNIQUE,
  password varchar(25) NOT NULL,
  passwordConfirm varchar(25) NOT NULL, 
  adresseMail varchar(25) NOT NULL, UNIQUE
);

INSERT INTO user (idUSer, username, password, passwordConfirm, adresseMail) VALUES
  ('1', 'admin', 'admin', 'admin', 'admin@gmail.com'),
  ('2', 'bureau', 'bureau', 'bureau', 'bureau@gmail.com'),
  ('3', 'adherent', 'adherent', 'adherent', 'adherent@gmail.com');
  
CREATE TABLE role (
  idRole bigint(11) AUTO_INCREMENT  PRIMARY KEY,
  name int(2) NOT NULL UNIQUE,
);

INSERT INTO role (id, name) VALUES
  ('1', '2'),
  ('2', '1'),
  ('3', '0');  
  
CREATE TABLE `user_role` (
  `idUser` bigint(11) NOT NULL,
  `idRole` bigint(11) NOT NULL,
  PRIMARY KEY (`idUSer`,`idRole`),
  KEY `idRole` (`idRole`),
  CONSTRAINT `user_role_ibfk_1` 
   FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  CONSTRAINT `user_role_ibfk_2` 
   FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
);  