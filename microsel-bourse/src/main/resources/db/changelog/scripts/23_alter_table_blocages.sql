ALTER TABLE blocages
  ADD adherent_username VARCHAR(255) NOT NULL
    AFTER adherent_id;