DROP TABLE IF EXISTS `part`;
CREATE TABLE `part` (
    `part_id` BIGINT UNSIGNED NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
    `event_id` BIGINT UNSIGNED NOT NULL,
    `part_id` BIGINT UNSIGNED NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    CONSTRAINT `event_part_fk` FOREIGN KEY (`part_id`) REFERENCES `part` (`part_id`),
    PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `training`;
CREATE TABLE `training` (
    `training_id` BIGINT UNSIGNED NOT NULL,
    `event_id` BIGINT UNSIGNED NOT NULL,
    `count` SMALLINT UNSIGNED NOT NULL,
    `weight` FLOAT UNSIGNED,
    `created_at` DATETIME NOT NULL,
    CONSTRAINT `training_event_fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
    PRIMARY KEY (`training_id`),
    KEY `created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

