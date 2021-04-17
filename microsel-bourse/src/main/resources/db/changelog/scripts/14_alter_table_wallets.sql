ALTER TABLE wallets  
  ADD titulaire_id varchar(36) NOT NULL UNIQUE
    AFTER wallet_id;
  
    