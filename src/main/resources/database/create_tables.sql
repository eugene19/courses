CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`user_roles`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`courses`
(
    `id`             INT          NOT NULL AUTO_INCREMENT,
    `summary`        VARCHAR(255) NOT NULL,
    `description`    LONGTEXT     NOT NULL,
    `students_limit` INT          NULL,
    `materials_path` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`users`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `login`      VARCHAR(255) NOT NULL UNIQUE,
    `password`   VARCHAR(255) NOT NULL,
    `surname`    VARCHAR(45)  NOT NULL,
    `name`       VARCHAR(45)  NOT NULL,
    `email`      VARCHAR(255) NOT NULL,
    `birthday`   DATE         NOT NULL,
    `role_id`    INT          NOT NULL,
    `photo_path` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `ROLE_ID_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `ROLE_ID`
        FOREIGN KEY (`role_id`)
            REFERENCES `COURSES_MANAGER`.`user_roles` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`course_statuses`
(
    `id`     INT          NOT NULL AUTO_INCREMENT,
    `status` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`course_results`
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `mark`    TINYINT      NOT NULL,
    `comment` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`course_runs`
(
    `id`          INT  NOT NULL AUTO_INCREMENT,
    `course_id`   INT  NOT NULL,
    `start_date`  DATE NULL,
    `end_date`    DATE NULL,
    `lecturer_id` INT  NULL,
    `status_id`   INT  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `COURSE_ID_UNIQUE` (`course_id` ASC) VISIBLE,
    INDEX `LECTURER_ID_idx` (`lecturer_id` ASC) VISIBLE,
    INDEX `STATUS_ID_idx` (`status_id` ASC) VISIBLE,
    CONSTRAINT `COURSE_ID`
        FOREIGN KEY (`course_id`)
            REFERENCES `COURSES_MANAGER`.`courses` (`id`),
    CONSTRAINT `LECTURER_ID`
        FOREIGN KEY (`lecturer_id`)
            REFERENCES `COURSES_MANAGER`.`users` (`id`),
    CONSTRAINT `STATUS_ID`
        FOREIGN KEY (`status_id`)
            REFERENCES `COURSES_MANAGER`.`course_statuses` (`id`)
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`user_course_statuses`
(
    `id`     INT         NOT NULL AUTO_INCREMENT,
    `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `COURSES_MANAGER`.`user_courses`
(
    `id`                    INT NOT NULL AUTO_INCREMENT,
    `user_id`               INT NOT NULL,
    `course_id`             INT NOT NULL,
    `course_result_id`      INT NULL,
    `user_course_status_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ID_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `USER_idx` (`user_id` ASC) VISIBLE,
    INDEX `COURSE_idx` (`course_id` ASC) VISIBLE,
    INDEX `USER_COURSE_STATUS_ID_idx` (`user_course_status_id` ASC) VISIBLE,
    CONSTRAINT `USER`
        FOREIGN KEY (`user_id`)
            REFERENCES `COURSES_MANAGER`.`users` (`id`),
    CONSTRAINT `COURSE`
        FOREIGN KEY (`course_id`)
            REFERENCES `COURSES_MANAGER`.`courses` (`id`),
    CONSTRAINT `COURSE_RESULT_ID`
        FOREIGN KEY (`course_result_id`)
            REFERENCES `COURSES_MANAGER`.`course_results` (`id`),
    CONSTRAINT `USER_COURSE_STATUS_ID`
        FOREIGN KEY (`user_course_status_id`)
            REFERENCES `COURSES_MANAGER`.`user_course_statuses` (`id`)
)
    DEFAULT CHARACTER SET = utf8;

