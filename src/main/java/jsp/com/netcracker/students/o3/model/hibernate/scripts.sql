create table areas
(
    id          integer      not null
        constraint areas_pk
            primary key,
    area_name   varchar(100) not null,
    description text
);

alter table areas
    owner to postgres;

create unique index areas_id_uindex
    on areas (id);

create table templates
(
    id integer not null
        constraint templates_pk
            primary key,
    template_name varchar(100) not null,
    cost integer not null,
    description text
);

alter table templates owner to postgres;

create unique index templates_id_uindex
    on templates (id);


create table template_area_link
(
    templateid integer not null,
    areaid integer not null,
    constraint template_area_link_pk
        primary key (templateid, areaid)
);

alter table template_area_link owner to postgres;

create table last_id
(
    id     integer not null
        constraint last_id_pk
            primary key,
    lastid integer not null
);

alter table last_id
    owner to postgres;

create unique index last_id_id_uindex
    on last_id (id);

create table customers
(
    id integer not null
        constraint customers_pk
            primary key,
    name varchar(100) not null,
    login varchar(100) not null,
    password varchar(100) not null,
    moneybalance integer default 0 not null,
    areaid integer not null
        constraint areaid
            references areas
);

alter table customers owner to postgres;

create unique index customers_id_uindex
    on customers (id);

create table employees
(
    id integer not null
        constraint employees_pk
            primary key,
    name varchar(100) not null,
    login varchar(100) not null,
    password varchar(100) not null
);

alter table employees owner to postgres;

create unique index employees_id_uindex
    on employees (id);

create table services
(
    id integer not null
        constraint services_pk
            primary key,
    userid integer not null
        constraint userid
            references customers,
    templateid integer not null
        constraint templateid
            references templates,
    status varchar(100) not null,
    activationdate date
);

alter table services owner to postgres;

create unique index services_id_uindex
    on services (id);

create table orders
(
    id integer not null
        constraint orders_pk
            primary key,
    templateid integer not null
        constraint templateid
            references templates,
    serviceid integer not null
        constraint serviceid
            references services,
    employeeid integer,
    status varchar(100) not null,
    orderaction varchar(100) not null,
    creationdate date
);

alter table orders owner to postgres;

create unique index orders_id_uindex
    on orders (id);

