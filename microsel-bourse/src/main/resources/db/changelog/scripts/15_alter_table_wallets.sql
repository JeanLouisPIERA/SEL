ALTER TABLE wallets
  ADD titulaire_username VARCHAR(255) NOT NULL
    AFTER titulaire_id;
