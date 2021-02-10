INSERT INTO roles (ROLE_ID, NAME) VALUES
  ('1', '0'),
  ('2', '1'),
  ('3', '2'),  
  ('4', '3'),
  ('5', '4');
INSERT INTO users (USER_ID, USERNAME, PASSWORD, EMAIL, STATUT, DATE_ADHESION, DATE_CLOTURE_DEBUT, DATE_CLOTURE_FIN, DATE_BLOCAGE_DEBUT, DATE_BLOCAGE_FIN, 
DATE_BUREAU_DEBUT, DATE_BUREAU_FIN, DATE_ADMIN_DEBUT, DATE_ADMIN_FIN) VALUES
  ('1', 'adherent', 'adherent', 'adherent@gmail.com', '0', '2020-01-15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
  ('2', 'adherentlocked', 'adherentlocked', 'adherentlocked@gmail.com', '1', '2020-01-15', NULL, NULL, '2020-12-20', NULL, NULL, NULL, NULL, NULL),
  ('3', 'adherentclosed', 'adherentclosed', 'adherentclosed', '2', '2020-01-15', NULL, NULL, '2020-10-05', NULL, NULL, NULL, NULL, NULL),
  ('4', 'bureau', 'bureau', 'bureau@gmail.com', '0', '2020-01-15', NULL, NULL, NULL, NULL,'2020-01-15', NULL, NULL, NULL),
  ('5', 'admin', 'admin', 'admin@gmail.com', '0', '2020-01-15', NULL, NULL, NULL, NULL, NULL, NULL, '2020-01-15', NULL),
  ('6', 'admin2', 'admin2', 'admin2@gmail.com', '0', '2020-01-15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
  
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES
   ('1', '1'),
   ('2', '2'),
   ('3', '3'),
   ('4', '1'),
   ('4', '4'),
   ('5', '1'),
   ('5', '4'),
   ('5', '5'),
   ('6', '1'),
   ('6', '4'),
   ('6', '5'); 
