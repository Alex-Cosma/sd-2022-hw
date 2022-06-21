create table hibernate_sequence(
    next_val bigint null
);

create table app_user
(
    id       bigserial
        primary key,
    email    varchar(50)  not null
        constraint uk1j9d9a06i600gd43uu3km82jw
            unique,
    password varchar(120) not null,
    username varchar(20)  not null
        constraint uk3k4cplvh82srueuttfkwnylq0
            unique
);

create table customer
(
    address      varchar(216) ,
    birth_date   date         ,
    full_name    varchar(216) ,
    phone_number varchar(20) ,
    id           bigint       not null
        primary key
        constraint fkjlfgi6ceqby6oau1krf393ba7
            references app_user on delete cascade
);

create table device
(
    id      bigserial
        primary key,
    brand   varchar(256) not null,
    name    varchar(256) not null,
    fk_user bigint
        constraint fk6m1pcvg72ncf2j90c0cf44uer
            references customer on delete cascade
);

create table role
(
    id   bigserial
        primary key,
    name varchar(20)
);

create table ticket
(
    id          bigserial
        primary key,
    created_at  timestamp,
    description varchar(512) not null,
    status      varchar(255),
    fk_device   bigint
        constraint fk7wpan1bwdc2podnpaiepm7ya5
            references device on delete cascade
);

create table user_roles
(
    user_id bigint not null
        constraint fk6fql8djp64yp4q9b3qeyhr82b
            references app_user,
    role_id bigint not null
        constraint fkrhfovtciq1l558cw6udg0h0d3
            references role,
    primary key (user_id, role_id)
);