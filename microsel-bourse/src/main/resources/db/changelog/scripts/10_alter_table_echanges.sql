ALTER TABLE echanges
  ADD emetteur_username varchar(255) NOT NULL
    AFTER recepteur_id,
  ADD recepteur_username varchar(255) NOT NULL
    AFTER emetteur_username;