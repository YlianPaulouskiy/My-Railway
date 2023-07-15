DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users_passengers;
DROP TABLE IF EXISTS passengers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS wagons;
DROP TABLE IF EXISTS routes_stations;
DROP TABLE IF EXISTS stations;
DROP TABLE IF EXISTS trains;


CREATE TABLE trains
(
    id bigserial PRIMARY KEY,
    code       varchar(256) NOT NULL UNIQUE ,
    type       varchar(256) NOT NULL -- можно вынести в отдельную таблицу
);

CREATE TABLE stations
(
    id   serial PRIMARY KEY,
    name varchar(256) NOT NULL UNIQUE
);

CREATE TABLE routes_stations
(
    id             bigserial PRIMARY KEY,
    station_id     int    NOT NULL REFERENCES stations (id) ON DELETE CASCADE,
    train_id bigint REFERENCES  trains(id) ON UPDATE CASCADE,
    departure_time timestamp,
    arrival_time   timestamp
);

CREATE TABLE wagons
(
    id         bigserial PRIMARY KEY,
    no         smallint     NOT NULL,
    type       varchar(128) NOT NULL, -- можно вынести в отдельную таблицу
    train_id bigint NOT NULL REFERENCES trains (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (no, train_id)
);

CREATE TABLE roles
(
    id   serial PRIMARY KEY,
    role varchar(128) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id        bigserial PRIMARY KEY,
    name      varchar(128) NOT NULL,
    last_name varchar(128) NOT NULL,
    email     varchar(256) NOT NULL UNIQUE,
    password  varchar(256) NOT NULL,
    avatar varchar,
    role_id   int          NOT NULL REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE passengers
(
    id          bigserial PRIMARY KEY,
    name        varchar(128) NOT NULL,
    last_name   varchar(128) NOT NULL,
    middle_name varchar(128) NOT NULL,
    gender      varchar(7)         NOT NULL,
    document    varchar(256) NOT NULL, -- можно вынести в отдельную таблицу
    document_no varchar(128) NOT NULL,
    UNIQUE (document, document_no)
);

create TABLE users_passengers
(
    id           bigserial PRIMARY KEY,
    user_id      bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    passenger_id bigint NOT NULL REFERENCES passengers (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id bigserial PRIMARY KEY,
    no      varchar(128) NOT NULL UNIQUE,
    status  varchar(128) NOT NULL, -- можно вынести в отдельную таблицу
    registration_time timestamp NOT NULL DEFAULT now(),
    payed_time timestamp,
    user_id bigint       NOT NULL REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE tickets
(
    id       bigserial PRIMARY KEY,
    from_id bigint REFERENCES routes_stations(id) ON UPDATE CASCADE ON DELETE CASCADE,
    to_id bigint REFERENCES routes_stations(id) ON UPDATE CASCADE ON DELETE CASCADE,
    order_id bigint REFERENCES orders (id) ON UPDATE CASCADE ON DELETE CASCADE,
    passenger_id bigint REFERENCES passengers(id)
);

CREATE TABLE seats
(
    id        bigserial PRIMARY KEY,
    no        smallint      NOT NULL,
    cost decimal(8,2) NOT NULL,
    type      varchar(256)  NOT NULL, -- можно вынести в отдельную таблицу
    wagon_id  bigint        NOT NULL REFERENCES wagons (id) ON UPDATE CASCADE ON DELETE CASCADE,
    ticket_id bigint UNIQUE REFERENCES tickets (id) ON UPDATE CASCADE ON DELETE SET NULL
);

