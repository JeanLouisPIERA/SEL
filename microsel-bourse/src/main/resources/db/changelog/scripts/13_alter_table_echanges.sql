ALTER TABLE echanges  
  ADD emetteur_mail varchar(255) NOT NULL
    AFTER titre,
  ADD recepteur_mail varchar(255) NOT NULL
    AFTER emetteur_mail;