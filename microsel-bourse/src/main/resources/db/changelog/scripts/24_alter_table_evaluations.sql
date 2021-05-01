ALTER TABLE evaluations
  ADD is_moderated BOOLEAN
    AFTER date_evaluation;