alter table "users"
    add column (
        `created_on` timestamp not null default current_timestamp,
        `updated_on` timestamp not null default current_timestamp
        );