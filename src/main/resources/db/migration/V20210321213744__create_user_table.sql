drop table if exists "users";

create table "users"
(
    id       bigint primary key auto_increment,
    email    varchar2(100) not null,
    username varchar2(100) not null,
    password varchar2(255) not null,
    role     varchar2(100) not null,
    enabled  bit           not null
)