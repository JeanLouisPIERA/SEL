ALTER TABLE echanges
  DROP emetteur_mail;

ALTER TABLE echanges  
  ADD emetteur_mail varchar(50) NOT NULL
    AFTER titre,
  ADD recepteur_mail varchar(50) NOT NULL
    AFTER emetteur_mail;