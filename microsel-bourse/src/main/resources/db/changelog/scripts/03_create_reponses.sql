CREATE TABLE IF NOT EXISTS reponses (
  REPONSE_ID bigint(5) AUTO_INCREMENT,
  RECEPTEUR_ID bigint(5) NOT NULL,
  TRADE_TYPE int(5),
  TITRE varchar(100) NOT NULL,
  DESCRIPTION varchar(255) NOT NULL,
  IMAGE varchar(30),
  VILLE varchar(50) NOT NULL,
  CODE_POSTAL int(6) NOT NULL,
  VALEUR int(3) NOT NULL,
  DATE_ECHEANCE DATE,
  DATE_REPONSE DATE,
  PROPOSITION_ID bigint(5),
  PRIMARY KEY (REPONSE_ID),
  KEY PROPOSITION_ID (PROPOSITION_ID),
  CONSTRAINT REPONSES_IFBK_1 FOREIGN KEY (PROPOSITION_ID) REFERENCES propositions (PROPOSITION_ID)
  );
  