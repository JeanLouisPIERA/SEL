ALTER TABLE propositions
  ADD emetteur_username VARCHAR(255) NOT NULL
    AFTER emetteur_id;