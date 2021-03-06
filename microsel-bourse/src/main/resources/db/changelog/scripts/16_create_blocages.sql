CREATE TABLE IF NOT EXISTS blocages (
  BLOCAGE_ID bigint(5) AUTO_INCREMENT,
  ADHERENT_ID varchar(36) NOT NULL,
  DATE_DEBUT_BLOCAGE DATE,
  DATE_FIN_BLOCAGE DATE,
  STATUT_BLOCAGE int(5),
  ECHANGE_ID bigint(5),
  PRIMARY KEY (BLOCAGE_ID),
  KEY ECHANGE_ID (ECHANGE_ID),
  CONSTRAINT BLOCAGES_IFBK_1 FOREIGN KEY (ECHANGE_ID) REFERENCES echanges (ECHANGE_ID)
  );
  