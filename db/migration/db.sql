create table accounts
(
    id       serial
        constraint accounts_pkey
            primary key,
    password varchar(250),
    email    varchar(250) not null
        constraint accounts_email_key
            unique,
    number   varchar(250) not null
        constraint accounts_number_key
            unique
);

alter table accounts
    owner to postgres;

create table planes
(
    id                  serial
        constraint planes_pkey
            primary key,
    number_of_seats     integer,
    weight              numeric,
    cruising_speed      numeric,
    model               varchar(250),
    company             varchar(250),
    max_flight_altitude numeric,
    max_range_of_flight numeric
);

alter table planes
    owner to postgres;

create table flights
(
    id                   serial
        constraint flights_pkey
            primary key,
    departure            timestamp,
    arrival              timestamp,
    number_of_free_seats integer,
    start_airport        varchar(250),
    final_airport        varchar(250),
    plane_id             integer
        constraint flights_planeid_fkey
            references planes
);

alter table flights
    owner to postgres;

create table tickets
(
    id              serial
        constraint tickets_pkey
            primary key,
    account_id      integer
        constraint tickets_accountid_fkey
            references accounts,
    flight_id       integer
        constraint tickets_flightid_fkey
            references flights,
    number_of_seats integer,
    active          boolean
);

alter table tickets
    owner to postgres;

create table seats
(
    ticket_id      integer
        constraint seats_ticketid_fkey
            references tickets,
    number_of_seat integer,
    id             serial
);

alter table seats
    owner to postgres;

create table t_role
(
    id   bigint not null
        constraint t_role_pkey
            primary key,
    name varchar(255)
);

alter table t_role
    owner to postgres;

create table accounts_roles
(
    account_id integer not null
        constraint fkt44duw96d6v8xrapfo4ff2up6
            references accounts,
    roles_id   bigint  not null
        constraint fk3jaw18kbweb5oty55bes1cyw8
            references t_role,
    constraint accounts_roles_pkey
        primary key (account_id, roles_id)
);

alter table accounts_roles
    owner to postgres;

create table tickets_seat_set
(
    ticket_id   integer not null
        constraint fkm6ftrvqhre2vid2oeahvtxghi
            references tickets,
    seat_set_id integer not null
        constraint uk_rinmw8priwx2scgkn2tt63ocs
            unique,
    constraint tickets_seat_set_pkey
        primary key (ticket_id, seat_set_id)
);

alter table tickets_seat_set
    owner to postgres;

