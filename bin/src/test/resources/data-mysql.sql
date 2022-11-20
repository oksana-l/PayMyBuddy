CREATE TABLE IF NOT EXISTS transaction (
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  sender_id INT NOT NULL,
  recipient_id INT NOT NULL,
  date DATE,
  description VARCHAR(255),
  amount DECIMAL(10,2),
  FOREIGN KEY (sender_id) REFERENCES user(id),
  FOREIGN KEY (recipient_id) REFERENCES user(id)
);

INSERT INTO transaction (sender_id, recipient_id, date, description, amount) values (2, 3, null, null, 500);
INSERT INTO transaction (sender_id, recipient_id, date, description, amount) values (3, 2, null, null, 100);
INSERT INTO transaction (sender_id, recipient_id, date, description, amount) values (4, 1, null, null, 10);
INSERT INTO transaction (sender_id, recipient_id, date, description, amount) values (1, 2, null, null, 100);
INSERT INTO transaction (sender_id, recipient_id, date, description, amount) values (1, 3, null, null, 200);