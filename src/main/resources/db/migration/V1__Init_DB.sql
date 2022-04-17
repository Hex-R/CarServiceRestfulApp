create sequence hibernate_sequence start 4 increment 1;

create table car_service
(
    id          int8          not null,
    type        varchar(255)  not null,
    name        varchar(255)  not null,
    description varchar(2048) not null,
    price       int4          not null,
    primary key (id)
);
create table service_order
(
    id             int8      not null,
    placed_at      timestamp not null,
    execution_date timestamp not null,
    is_completed   boolean   not null,
    user_id        int8      not null,
    primary key (id)
);
create table service_order_services
(
    service_order_id int8 not null,
    services_id      int8 not null
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table usr
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
create table usr_service_orders
(
    user_id           int8 not null,
    service_orders_id int8 not null
);
alter table if exists usr_service_orders
    add constraint UK_bmpo638ynmhfra31bh5uxr981 unique (service_orders_id);
alter table if exists service_order
    add constraint FKgsfocsygygs4egbly0siwjhqy foreign key (user_id) references usr;
alter table if exists service_order_services
    add constraint FKmbixkrxgp4ppu59xwkxbm442 foreign key (services_id) references car_service;
alter table if exists service_order_services
    add constraint FKev9bddfmbwoaexnq9m00ei08w foreign key (service_order_id) references service_order;
alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;
alter table if exists usr_service_orders
    add constraint FKn9ewavjpg73s40wjlvqoh5d2g foreign key (service_orders_id) references service_order;
alter table if exists usr_service_orders
    add constraint FK5iqqbnyblmju21h0mwq8exjja foreign key (user_id) references usr