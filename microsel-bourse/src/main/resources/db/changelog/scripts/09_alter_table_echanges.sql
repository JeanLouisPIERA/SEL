ALTER TABLE echanges
  ADD emetteur_id varchar(36) NOT NULL
    AFTER date_enregistrement,
  ADD recepteur_id varchar(36) NOT NULL
    AFTER emetteur_id;