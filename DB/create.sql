create table cargos (
                        id_roles		numeric not null,
                        role_name		text not null,
                        primary key(id_roles)
);

create table usuarios (
                          id_user		numeric not null,
                          id_roles	numeric not null,
                          user_name	text not null,
                          email		text not null,
                          user_password	text not null,
                          primary key(id_user),
                          constraint fk_cargos_usuarios foreign key (id_roles) references cargos (id_roles)
);

create sequence seq_usuario
    increment 1
start 1;

create sequence seq_cargo
    increment 1
start 1;

insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_FACILITATOR');
insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_MEMBER');

insert into usuarios (id_user, id_roles, user_name, email, user_password) values (nextval('seq_usuario'), 1, 'DANYLLO', 'danyllo@gmail.com', '$2a$10$5rMcXmTxuoQibO.RvqX6B.gEV0mxHjoZO8Sqx0JoFuFAddV2fYAX2');