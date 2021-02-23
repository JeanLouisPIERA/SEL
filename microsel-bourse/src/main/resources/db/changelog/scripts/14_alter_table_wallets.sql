ALTER TABLE wallets  
  ADD titulaire_id bigint(5) NOT NULL UNIQUE
    AFTER wallet_id;
  
    