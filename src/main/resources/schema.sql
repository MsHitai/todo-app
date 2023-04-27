CREATE TABLE if not exists TASK (
       id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
       description VARCHAR(100) NOT NULL,
       due_Date DATE NOT NULL,
       is_Done BOOL
 );