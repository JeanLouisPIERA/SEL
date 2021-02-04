CREATE TABLE IF NOT EXISTS roles (
ROLE_ID bigint(5) AUTO_INCREMENT,
NAME int(5),
PRIMARY KEY(ROLE_ID)
)
GO
 
CREATE TABLE IF NOT EXISTS users (
USER_ID bigint(5) AUTO_INCREMENT,
USERNAME varchar(100) NOT NULL UNIQUE,
PASSWORD varchar(25) NOT NULL,
EMAIL varchar(100) NOT NULL UNIQUE,
DATE_ADHESION_DEBUT DATE NOT NULL, 
DATE_ADHESION_FIN DATE, 
DATE_BLOCAGE_DEBUT DATE, 
DATE_BLOCAGE_FIN DATE,
PRIMARY KEY(USER_ID)
)
GO


  

  
CREATE TABLE IF NOT EXISTS users_roles (
USER_ID bigint(5) NOT NULL,
ROLE_ID bigint(5) NOT NULL,
FOREIGN KEY (USER_ID) REFERENCES users (USER_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY (ROLE_ID) REFERENCES roles (ROLE_ID) ON DELETE RESTRICT ON UPDATE CASCADE,
PRIMARY KEY (USER_ID, ROLE_ID)
)  
GO

  