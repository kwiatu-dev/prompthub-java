SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `refresh_tokens` (
      `created_at` datetime(6) DEFAULT NULL,
      `expires` datetime(6) DEFAULT NULL,
      `revoked_at` datetime(6) DEFAULT NULL,
      `id` varchar(255) NOT NULL,
      `reason_revoked` varchar(255) DEFAULT NULL,
      `replace_by_token` varchar(255) DEFAULT NULL,
      `token` varchar(255) DEFAULT NULL,
      `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `refresh_tokens`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `UK_ghpmfn23vmxfu3spu3lfg4r2d` (`token`),
    ADD KEY `FKqpku0cdowlnpr3pg7j2fcf7li` (`replace_by_token`),
    ADD KEY `FKjwc9veyjcjfkej6rnnbsijfvh` (`user_id`);

ALTER TABLE `refresh_tokens`
    ADD CONSTRAINT `FKjwc9veyjcjfkej6rnnbsijfvh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKqpku0cdowlnpr3pg7j2fcf7li` FOREIGN KEY (`replace_by_token`) REFERENCES `refresh_tokens` (`token`) ON DELETE NO ACTION;

COMMIT;