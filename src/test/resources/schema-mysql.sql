--ALTER TABLE user_connection
--DROP CONSTRAINT FKkmgvq6o2yvbdc9enyhrcib6tk;
--DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL
);

INSERT INTO user (user_name, email, password) values ('Oksana', 'oksana@moi.meme', 'pass');
INSERT INTO user (user_name, email, password) values ('Polina', 'polina@moi.meme', 'pass');
INSERT INTO user (user_name, email, password) values ('Bouboule', 'bouboule@moi.meme', 'pass');
INSERT INTO user (user_name, email, password) values ('Monk', 'monk@moi.meme', 'pass');
