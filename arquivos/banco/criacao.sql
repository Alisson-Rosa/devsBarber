-- Tabela usuario
create sequence usuario_id_seq;
alter sequence usuario_id_seq owner to devs_barber;

create table usuario
(
    id     integer default nextval('usuario_id_seq'::regclass) not null
        primary key,
    email varchar(50) not null,
    password varchar (100) not null,
    name varchar(50) not null,
    enable boolean not null,
    username varchar (50) not null constraint usuario_uk unique ,
    birthdate date not null
);

create index usuario_idx1 on usuario (username);

alter sequence usuario_id_seq owned by usuario.id;

-- Tabela Barber
create sequence barber_id_seq;
alter sequence barber_id_seq owner to devs_barber;

create table barber
(
    id     integer default nextval('barber_id_seq'::regclass) not null
        primary key,
    name varchar(50) not null,
    birthdate date not null
);

create index barber_index1 on barber (name);
alter sequence barber_id_seq owned by barber.id;

-- Tabela Role
create sequence user_role_id_seq;
alter sequence user_role_id_seq owner to devs_barber;

create table usuario_role
(
    id     integer default nextval('user_role_idx1'::regclass) not null
        primary key,
    usuario_id   integer constraint user_role_fk1 references usuario
        on delete cascade,
    role_id   integer constraint user_role_fk2 references role
        on delete cascade
);

create index user_role_idx1 on user_role (usuario_id);
create index user_role_idx2 on user_role (role_id);

alter sequence user_role_id_seq owned by user_role.id;

alter table user_role add constraint user_role_uk unique (usuario_id, role_id)

-- cut
create sequence cut_id_seq;
alter sequence cut_id_seq owner to devs_barber;

create table cut
(
    id     integer default nextval('cut_id_seq'::regclass) not null
        primary key,
    typecut varchar (30) not null ,
    value double precision not null ,
    time time with time zone not null
);

create index cut_idx1 on cut (typecut);

alter sequence cut_id_seq owned by cut.id;

-- schedule
create sequence schedule_id_seq;
alter sequence schedule_id_seq owner to devs_barber;

create table schedule
(
    id     integer default nextval('cut_id_seq'::regclass) not null
        primary key,
    date date not null,
    hours time with time zone,
    client_id integer constraint schedule_fk1 references usuario on delete cascade,
    barber_id integer constraint schedule_fk2 references barber on delete cascade,
    cut_id integer constraint schedule_fk3 references cut on delete cascade
);

create index schedule_idx1 on schedule (client_id);
create index schedule_idx2 on schedule (barber_id);
create index schedule_idx3 on schedule (cut_id);

alter sequence schedule_id_seq owned by schedule.id;