ALTER TABLE transactions
  ADD emetteur_id VARCHAR(255) 
    AFTER recepteur_username,
  ADD recepteur_id VARCHAR(255) 
    AFTER emetteur_id;    