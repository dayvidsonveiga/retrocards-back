create table usuarios (
                          id_user		numeric not null,
                          user_name	text not null,
                          email		text not null,
                          user_password	numeric not null,
                          primary key(id_user)
);

create table cargos (
                        id_roles		numeric not null,
                        role_name		text not null,
                        primary key(id_roles)
);

create table usuario_cargo (
                               id_user_roles	numeric not null,
                               id_user		numeric not null,
                               id_roles	numeric not null,
                               primary key(id_user, id_roles),
                               constraint fk_usuario_cargo_cargos foreign key (id_roles) references cargos (id_roles),
                               constraint fk_usuario_cargo_usuarios foreign key (id_user) references usuarios (id_user)
);

create sequence seq_usuario
    increment 1
start 1;

create sequence seq_cargo
    increment 1
start 1;

create sequence seq_usuario_cargo
    increment 1
start 1;

insert into usuarios (id_user, user_name, email, user_password) values (nextval('seq_usuario'), 'DANYLLO', 'danyllo@gmail.com', '123');

insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_FACILITATOR');
insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_MEMBER');

insert into usuario_cargo (id_user_roles, id_user, id_roles) values (nextval('seq_usuario_cargo'), 1, 1);