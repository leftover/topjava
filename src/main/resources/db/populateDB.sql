DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, description, calories)
VALUES (100000, 'Breakfast', 500),
       (100000, 'Launch', 1000),
       (100001, 'Bar', 430),
       (100001, 'Snack', 650),
       (100000, 'Diner', 500),
       (100000, 'Snack', 100);
