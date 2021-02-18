ALTER TABLE echanges
  ADD titre varchar(50) NOT NULL
    AFTER date_echeance;