ALTER TABLE echanges
  ADD date_echeance DATE NOT NULL
    AFTER recepteur_username;