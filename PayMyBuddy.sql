CREATE TABLE `User` (
  `id` int PRIMARY KEY,
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
  `user_id` int,
  `connected_user_id` int,
  PRIMARY KEY (`user_id`, `connected_user_id`)
);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`sender_id`) REFERENCES `User` (`id`);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`recipient_id`) REFERENCES `User` (`id`);

ALTER TABLE `Connection` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Connection` ADD FOREIGN KEY (`connected_user_id`) REFERENCES `User` (`id`);
