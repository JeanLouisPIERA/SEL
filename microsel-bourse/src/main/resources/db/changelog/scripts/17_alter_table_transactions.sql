ALTER TABLE transactions
  ADD titre_echange VARCHAR(100) NOT NULL
    AFTER date_transaction,
  ADD emetteur_username VARCHAR(255) NOT NULL
    AFTER titre_echange,  
  ADD recepteur_username VARCHAR(255) NOT NULL
    AFTER emetteur_username;