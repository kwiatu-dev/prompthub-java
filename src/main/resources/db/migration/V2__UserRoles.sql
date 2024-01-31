SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `roles` (
     `id` varchar(255) NOT NULL,
     `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `users_roles` (
   `role_id` varchar(255) NOT NULL,
   `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `roles`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `users_roles`
    ADD PRIMARY KEY (`role_id`,`user_id`),
    ADD KEY `FKgd3iendaoyh04b95ykqise6qh` (`user_id`);

ALTER TABLE `users_roles`
    ADD CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

COMMIT;