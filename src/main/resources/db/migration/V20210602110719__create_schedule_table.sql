drop table if exists "schedules";

create table "schedules"
(
    `id`             bigint primary key auto_increment,
    `name`           varchar2(100) not null,
    `description`    varchar2(255),
    `start_of_range` date,
    `end_of_range`   date,
    `status`         varchar2(50)  not null,
    `image_path`     varchar(255)  not null,
    `owner_id`       bigint        not null,

    `created_on`     timestamp     not null default current_timestamp,
    `updated_on`     timestamp     not null default current_timestamp,

    foreign key (`owner_id`)
        references "users" (`id`)
);