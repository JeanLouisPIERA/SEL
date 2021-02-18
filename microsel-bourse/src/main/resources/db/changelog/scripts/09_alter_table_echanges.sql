ALTER TABLE echanges
  ADD emetteur_id bigint(5) NOT NULL
    AFTER date_enregistrement,
  ADD recepteur_id bigint(5) NOT NULL
    AFTER emetteur_id;