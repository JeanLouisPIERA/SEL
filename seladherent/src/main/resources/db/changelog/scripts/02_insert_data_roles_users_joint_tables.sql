INSERT INTO roles (ROLE_ID, NAME) VALUES
  ('1', '0'),
  ('2', '1'),
  ('3', '2'),  
  ('4', '3'),
  ('5', '4');
INSERT INTO users (USER_ID, USERNAME, PASSWORD, EMAIL, DATE_ADHESION_DEBUT, DATE_ADHESION_FIN, DATE_BLOCAGE_DEBUT, DATE_BLOCAGE_FIN) VALUES
  ('1', 'adherent', 'adherent', 'adherent@gmail.com', '2020-01-15', NULL, NULL, NULL),
  ('2', 'bureau/lockedAccount/adherent2', '123456/adherent2', 'adherent2@gmail.com', '2020-01-15', NULL, '2020-12-20', NULL),
  ('3', 'closedAccount/12020-10-05', 'closedAccount/12020-10-05', 'closeadAccount/12020-10-05', '2020-01-15', '2020-10-05', NULL, NULL),
  ('4', 'bureau', 'bureau', 'bureau@gmail.com', '2020-01-15', NULL, NULL, NULL),
  ('5', 'admin', 'admin', 'admin@gmail.com', '2020-01-15', NULL, NULL, NULL),
  ('6', 'admin2', 'admin2', 'admin2@gmail.com', '2020-01-15', NULL, NULL, NULL);
  
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

