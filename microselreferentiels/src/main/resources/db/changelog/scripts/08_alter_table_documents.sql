ALTER TABLE documents
  ADD DATE_PUBLICATION DATE
    AFTER DATE_LAST_UPDATE, 
  ADD DATE_ARCHIVAGE DATE
    AFTER DATE_PUBLICATION;	

 
 