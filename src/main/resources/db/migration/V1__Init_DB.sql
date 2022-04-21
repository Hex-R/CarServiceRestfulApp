create sequence hibernate_sequence start 36 increment 1;

create table users
(
    id              int8        not null,
    active          boolean     not null,
    username        varchar(30) not null,
    password        varchar(70) not null,
    email           varchar(50) not null,
    phone_number    varchar(12) not null,
    activation_code varchar(255),
    primary key (id)
);

create table user_roles
(
    user_id int8 not null,
    roles   varchar(255)
);

create table services
(
    id          int8          not null,
    type        varchar(255)  not null,
    category    varchar(255)  not null,
    name        varchar(255)  not null,
    description varchar(2048) not null,
    price       int4          not null,
    primary key (id)
);

create table orders
(
    id             int8      not null,
    placed_at      timestamp not null,
    execution_date timestamp not null,
    is_completed   boolean   not null,
    user_id        int8      not null,
    primary key (id)
);

create table orders_services
(
    order_id   int8 not null,
    services_id int8 not null
);


alter table if exists user_roles
    add constraint user_roles_user_fk foreign key (user_id) references users;

alter table if exists orders
    add constraint order_user_fk foreign key (user_id) references users;

alter table if exists orders_services
    add constraint order_services_service_fk foreign key (services_id) references services;
alter table if exists orders_services
    add constraint order_services_order_fk foreign key (order_id) references orders;

