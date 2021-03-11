ALTER TABLE users
  ADD first_name VARCHAR(25) NOT NULL
    AFTER email,
  ADD last_name VARCHAR(25) NOT NULL
    AFTER first_name,  
  ADD status_code int(3)
    AFTER last_name,    
  ADD status VARCHAR(25)
    AFTER status_code;