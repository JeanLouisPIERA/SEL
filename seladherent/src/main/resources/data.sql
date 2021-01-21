-- DROP TABLE IF EXISTS user_role;
-- DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS role;
-- 
-- CREATE TABLE user (
--   idUser bigint  PRIMARY KEY AUTO_INCREMENT,
--   username varchar(25) NOT NULL UNIQUE,
--   password varchar(25) NOT NULL,
--   passwordConfirm varchar(25) NOT NULL, 
--   adresseMail varchar(25) NOT NULL UNIQUE,
-- );
-- GO


INSERT INTO role (role_id, name) VALUES
  ('1', '2'),
  ('2', '1'),
  ('3', '0');  

INSERT INTO user (user_id, username, password, email, role_id) VALUES
  ('1', 'admin', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'admin@gmail.com','1'),
  ('2', 'bureau', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'bureau@gmail.com','2'),
  ('3', 'adherent', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'adherent@gmail.com','3');
  
-- CREATE TABLE role (
--   idRole bigint(11) AUTO_INCREMENT  PRIMARY KEY,
--   name int(2) NOT NULL UNIQUE,
-- );


  

  
-- CREATE TABLE `user_role` (
--   `idUser` bigint(11) NOT NULL,
--   `idRole` bigint(11) NOT NULL,
--   PRIMARY KEY (`idUSer`,`idRole`),
--   KEY `idRole` (`idRole`),
--   CONSTRAINT `user_role_ibfk_1` 
--    FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
--   CONSTRAINT `user_role_ibfk_2` 
--    FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`)
-- );  