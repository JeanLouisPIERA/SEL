CREATE TABLE IF NOT EXISTS documents (
DOCUMENT_ID bigint(5) AUTO_INCREMENT,
TITRE varchar(25) NOT NULL,
AUTEUR_ID bigint(5) NOT NULL, 
AUTEUR_USERNAME varchar(25) NOT NULL, 
DATE_CREATION DATE, 
DATE_LAST_UPDATE DATE, 
IMAGE varchar(25), 
CONTENU varchar(255) NOT NULL, 
STATUT_DOCUMENT int(3),
TYPEDOCUMENT_ID bigint(5),
PRIMARY KEY (DOCUMENT_ID),
KEY TYPEDOCUMENT_ID (TYPEDOCUMENT_ID),
CONSTRAINT DOCUMENTS_IFBK_1 FOREIGN KEY (TYPEDOCUMENT_ID) REFERENCES typedocuments (TYPEDOCUMENT_ID)
)
;
