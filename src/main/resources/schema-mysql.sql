DROP TABLE IF EXISTS user;

CREATE  TABLE user (
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (email)
);
  
--CREATE TABLE authorities (
--  email VARCHAR(50) NOT NULL,
--  authority VARCHAR(50) NOT NULL,
--  FOREIGN KEY (email) REFERENCES bael_users(email)
--);