CREATE DATABASE IF NOT EXISTS auth;
USE auth;
CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    authority VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

INSERT INTO role (authority, name) VALUES ('ROLE_USER', 'user');
INSERT INTO role (authority, name) VALUES ('ROLE_INSTRUCTOR', 'INSTRUCTOR');
INSERT INTO role (authority, name) VALUES ('ROLE_ADMIN', 'ADMIN');

INSERT INTO `auth`.`users`
(`enabled`,
`password`,
`username`)
VALUES
(`1`,
`$2a$10$P/v6I/vjkh8Xg48ZS2RwwOMyHlIFTqahNa3MZ9Hyt0Jp7..cI44/i`,
`testuser`);

INSERT INTO `auth`.`users`
(`enabled`,
`password`,
`username`)
VALUES
(`1`,
`$2a$10$F02crHI22Q4oW2G8nYB.f.HQMy7xzqkwCp9TW7RB6LAQb4CmiuAKa`,
`testadmin`);

INSERT INTO `auth`.`users`
(`enabled`,
`password`,
`username`)
VALUES
(`1`,
`$2a$10$DEswQA7TM9.PGs2WUPWKMOx1aqQqrXtGvIO4c.CAhARLKCUDsECwC`,
`testinstructor`);

INSERT INTO `auth`.`user_roles`
(`role_id`,
`user_id`)
VALUES
(
`1`,
`1`);

INSERT INTO `auth`.`user_roles`
(`role_id`,
`user_id`)
VALUES
(
`3`,
`2`);

INSERT INTO `auth`.`user_roles`
(`role_id`,
`user_id`)
VALUES
(
`2`,
`3`);