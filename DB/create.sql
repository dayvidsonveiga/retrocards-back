create table cargos (
                        id_roles		numeric not null,
                        role_name		text not null,
                        primary key(id_roles)
);

create table usuarios (
                          id_user		numeric not null,
                          id_roles	numeric not null,
                          user_name	text not null,
                          email		text unique not null,
                          user_password	text not null,
                          primary key(id_user),
                          constraint fk_cargos_usuarios foreign key (id_roles) references cargos (id_roles)
);

create table sprints (
                         id_sprint	numeric not null,
                         title		text not null,
                         start_date	date not null,
                         end_date	date not null,
                         primary key(id_sprint)
);

create table usuario_sprint (
                                id_usuario_sprint	numeric not null,
                                id_user numeric not null,
                                id_sprint numeric not null,
                                primary key(id_usuario_sprint),
                                constraint fk_usuarios_usuario_sprint foreign key (id_user) references usuarios (id_user),
                                constraint fk_sprints_usuario_sprint foreign key (id_sprint) references sprints (id_sprint)
);

create table kudo_boxs (
                           id_kudo_box		numeric not null,
                           id_sprint		numeric not null,
                           title			text not null,
                           end_date		date not null,
                           status			numeric not null,
                           primary key (id_kudo_box),
                           constraint fk_sprints_kudo_boxs foreign key (id_sprint) references sprints (id_sprint)
);

create table kudo_cards (
                            id_kudo_card	numeric not null,
                            id_kudo_box		numeric not null,
                            title			numeric not null,
                            create_date		date not null,
                            sender			text not null,
                            receiver		text not null,
                            primary key(id_kudo_card),
                            constraint fk_kudo_boxs_kudo_cards foreign key (id_kudo_box) references kudo_boxs (id_kudo_box)
);

create table retrospectivas (
                                id_retrospective	numeric not null,
                                id_sprint			numeric not null,
                                title				text not null,
                                occurred_date		date not null,
                                status				numeric not null,
                                primary key(id_retrospective),
                                constraint fk_sprints_retrospectivas foreign key (id_sprint) references sprints (id_sprint)
);

create table item_retrospectivas (
                                     id_item_retrospective	numeric not null,
                                     id_retrospective		numeric not null,
                                     item_type				numeric not null,
                                     title					text not null,
                                     description				text not null,
                                     primary key(id_item_retrospective),
                                     constraint fk_retrospectivas_item_retrospectivas foreign key (id_retrospective) references retrospectivas (id_retrospective)
);

create sequence seq_retrospectiva
    increment 1
start 1;

create sequence seq_item_retrospectiva
    increment 1
start 1;

create sequence seq_usuario
    increment 1
start 1;

create sequence seq_cargo
    increment 1
start 1;

create sequence seq_sprint
    increment 1
start 1;

create sequence seq_usuario_sprint
    increment 1
start 1;

create sequence seq_kudobox
    increment 1
start 1;

create sequence seq_kudocard
    increment 1
start 1;

insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_FACILITATOR');
insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_MEMBER');

insert into usuarios (id_user, id_roles, user_name, email, user_password) values (nextval('seq_usuario'), 1, 'DANYLLO', 'danyllo@gmail.com', '$2a$10$5rMcXmTxuoQibO.RvqX6B.gEV0mxHjoZO8Sqx0JoFuFAddV2fYAX2');

-------------------------------------------------------------------------------

-- INSERTS SPRINT'S

insert into sprints (id_sprint, title, start_date, end_date) values (nextval('seq_sprint'), 'Definição de projetos com o cliente da C&A', to_date('13-08-2022', 'DD-MM-YYYY'), to_date('13-08-2022', 'DD-MM-YYYY'));

insert into sprints (id_sprint, title, start_date, end_date) values (nextval('seq_sprint'), 'Definição das regras do Projeto A do cliente Sicredi', to_date('14-08-2022', 'DD-MM-YYYY'), to_date('14-08-2022', 'DD-MM-YYYY'));

insert into sprints (id_sprint, title, start_date, end_date) values (nextval('seq_sprint'), 'Levantamento e correções do projeto C do cliente Subway', to_date('15-08-2022', 'DD-MM-YYYY'), to_date('15-08-2022', 'DD-MM-YYYY'));

insert into sprints (id_sprint, title, start_date, end_date) values (nextval('seq_sprint'), 'Refatoração de códigos do Projeto B', to_date('16-08-2022', 'DD-MM-YYYY'), to_date('16-08-2022', 'DD-MM-YYYY'));

insert into sprints (id_sprint, title, start_date, end_date) values (nextval('seq_sprint'), 'Reunião com novo cliente ChinaInBox', to_date('17-08-2022', 'DD-MM-YYYY'), to_date('17-08-2022', 'DD-MM-YYYY'));