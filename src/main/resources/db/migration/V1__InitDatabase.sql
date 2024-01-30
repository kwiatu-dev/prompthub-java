SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `user` (
    `state` varchar(10) NOT NULL COMMENT 'Soft-delete indicator',
    `created_at` datetime(6) DEFAULT NULL,
    `deleted_at` datetime(6) DEFAULT NULL,
    `modified_at` datetime(6) DEFAULT NULL,
    `created_by` varchar(255) DEFAULT NULL,
    `deleted_by` varchar(255) DEFAULT NULL,
    `email` varchar(255) NOT NULL,
    `id` varchar(255) NOT NULL,
    `modified_by` varchar(255) DEFAULT NULL,
    `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `projects` (
    `state` varchar(10) NOT NULL COMMENT 'Soft-delete indicator',
    `created_at` datetime(6) DEFAULT NULL,
    `deleted_at` datetime(6) DEFAULT NULL,
    `modified_at` datetime(6) DEFAULT NULL,
    `name` varchar(128) NOT NULL,
    `description` varchar(256) NOT NULL,
    `created_by` varchar(255) DEFAULT NULL,
    `deleted_by` varchar(255) DEFAULT NULL,
    `id` varchar(255) NOT NULL,
    `modified_by` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `prompt` (
      `state` varchar(10) NOT NULL COMMENT 'Soft-delete indicator',
      `tokens` int(11) NOT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `deleted_at` datetime(6) DEFAULT NULL,
      `modified_at` datetime(6) DEFAULT NULL,
      `model` varchar(64) NOT NULL,
      `name` varchar(128) NOT NULL,
      `description` varchar(256) NOT NULL,
      `created_by` varchar(255) DEFAULT NULL,
      `deleted_by` varchar(255) DEFAULT NULL,
      `id` varchar(255) NOT NULL,
      `modified_by` varchar(255) DEFAULT NULL,
      `project_id` varchar(255) NOT NULL,
      `messages` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`messages`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `user`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKdltbr5t0nljpuuo4isxgslt82` (`created_by`),
    ADD KEY `FKsqpewmuv3ia5tdi2a3kxg9tr9` (`deleted_by`),
    ADD KEY `FKo4omy56oktgee21ndp4c7vkkb` (`modified_by`);

ALTER TABLE `projects`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKhe2fjwmi248e1kkspnk0plhu7` (`created_by`),
    ADD KEY `FKagnyj60ocgp5ox34n5b4341so` (`deleted_by`),
    ADD KEY `FKbscmighr8o34cxdg9xu6sqcon` (`modified_by`);

ALTER TABLE `prompt`
    ADD PRIMARY KEY (`id`),
    ADD KEY `FKspq5y6qc8e5xaj733427h0j8n` (`created_by`),
    ADD KEY `FK154ufj378cbch90y0wfw51bbm` (`deleted_by`),
    ADD KEY `FKko8ebx2xss8qj9l6bdu3csaci` (`modified_by`),
    ADD KEY `FKpmk0398lt3tvoudik9pneqah1` (`project_id`);

ALTER TABLE `user`
    ADD CONSTRAINT `FKdltbr5t0nljpuuo4isxgslt82` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKo4omy56oktgee21ndp4c7vkkb` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKsqpewmuv3ia5tdi2a3kxg9tr9` FOREIGN KEY (`deleted_by`) REFERENCES `user` (`id`);

ALTER TABLE `projects`
    ADD CONSTRAINT `FKagnyj60ocgp5ox34n5b4341so` FOREIGN KEY (`deleted_by`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKbscmighr8o34cxdg9xu6sqcon` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKhe2fjwmi248e1kkspnk0plhu7` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`);

ALTER TABLE `prompt`
    ADD CONSTRAINT `FK154ufj378cbch90y0wfw51bbm` FOREIGN KEY (`deleted_by`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKko8ebx2xss8qj9l6bdu3csaci` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`),
    ADD CONSTRAINT `FKpmk0398lt3tvoudik9pneqah1` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
    ADD CONSTRAINT `FKspq5y6qc8e5xaj733427h0j8n` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`);

COMMIT;