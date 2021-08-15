DROP TABLE IF EXISTS schedules;

CREATE TABLE schedules
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(100)                        NOT NULL,
    description    VARCHAR(255),
    start_of_range DATE,
    end_of_range   DATE,
    status         VARCHAR(50)                         NOT NULL,
    image_path     VARCHAR(255)                        NOT NULL,
    created_on     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_on     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    owner_id       BIGINT                              NOT NULL
        CONSTRAINT schedule_owner REFERENCES users (id)
);
