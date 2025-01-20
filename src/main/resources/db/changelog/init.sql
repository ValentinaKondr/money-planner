create table usr
(
    id       bigserial primary key,
    login    varchar(255) not null,
    password varchar(255) not null,

    unique (login)
);

create table category
(
    id      bigserial primary key,
    user_id int          not null,
    name    varchar(255) not null,
    "limit" float          not null,

    foreign key (user_id) references usr (id),
    unique (user_id, name)
);

create table operation
(
    id            bigserial primary key,
    user_id       int          not null,
    category_name varchar(255) not null,
    amount        float        not null,
    type          varchar(255) not null,

    foreign key (user_id) references usr (id),
    foreign key (user_id, category_name) references category (user_id, name)
);