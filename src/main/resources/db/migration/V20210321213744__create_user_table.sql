DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(100) NOT NULL,
    enabled  BOOLEAN      NOT NULL
)
