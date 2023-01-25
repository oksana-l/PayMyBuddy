CREATE TABLE `Account` (
  `id` int PRIMARY KEY,
  `user_name` varchar(50)
  `email` varchar(50),
  `password` varchar(50),
  `balance` float
);

CREATE TABLE `Transaction` (
  `id` int PRIMARY KEY,
  `sender_id` int,
  `recipient_id` int,
  `date` datetime,
  `description` varchar(255),
  `amount` float
);

CREATE TABLE `Connection` (
  `account_id` int,
  `connected_account_id` int,
  PRIMARY KEY (`account_id`, `connected_account_id`)
);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`sender_id`) REFERENCES `Account` (`id`);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`recipient_id`) REFERENCES `Account` (`id`);

ALTER TABLE `Connection` ADD FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`);

ALTER TABLE `Connection` ADD FOREIGN KEY (`connected_account_id`) REFERENCES `Account` (`id`);
