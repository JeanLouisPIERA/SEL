ALTER TABLE wallets
  ADD titulaire_username VARCHAR(25) NOT NULL
    AFTER titulaire_id;