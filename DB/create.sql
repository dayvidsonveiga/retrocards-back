create table cargos (
                        id_roles	numeric not null,
                        role_name	text not null,
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
                         start_date	timestamp not null,
                         end_date	timestamp not null,
                         status		text not null,
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
                           end_date		timestamp not null,
                           status			text not null,
                           primary key (id_kudo_box),
                           constraint fk_sprints_kudo_boxs foreign key (id_sprint) references sprints (id_sprint)
);

create table kudo_cards (
                            id_kudo_card	numeric not null,
                            id_creator		numeric not null,
                            id_kudo_box		numeric not null,
                            title			text not null,
                            description		text not null,
                            create_date		timestamp not null,
                            sender			text not null,
                            receiver		text not null,
                            primary key(id_kudo_card),
                            constraint fk_kudo_boxs_kudo_cards foreign key (id_kudo_box) references kudo_boxs (id_kudo_box)
);

create table retrospectivas (
                                id_retrospective	numeric not null,
                                id_sprint			numeric not null,
                                title				text not null,
                                occurred_date		timestamp not null,
                                status				text not null,
                                primary key(id_retrospective),
                                constraint fk_sprints_retrospectivas foreign key (id_sprint) references sprints (id_sprint)
);

create table item_retrospectivas (
                                     id_item_retrospective	numeric not null,
                                     id_retrospective		numeric not null,
                                     item_type				text not null,
                                     title					text not null,
                                     description				text not null,
                                     primary key(id_item_retrospective),
                                     constraint fk_retrospectivas_item_retrospectivas foreign key (id_retrospective) references retrospectivas (id_retrospective)
);

create table email (
                       id_email	numeric not null,
                       receiver	text not null,
                       subject		text not null,
                       send_date	timestamp not null,
                       primary key (id_email)
);

create sequence seq_email
    increment 1
start 1;

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
insert into cargos (id_roles, role_name) values (nextval('seq_cargo'), 'ROLE_ADMIN');

insert into usuarios (id_user, id_roles, user_name, email, user_password) values (nextval('seq_usuario'), 1, 'DANYLLO', 'danyllo@gmail.com', '$2a$10$5rMcXmTxuoQibO.RvqX6B.gEV0mxHjoZO8Sqx0JoFuFAddV2fYAX2');
insert into usuarios (id_user, id_roles, user_name, email, user_password) values (nextval('seq_usuario'), 1, 'FACILITATOR', 'facilitator@gmail.com', '$2a$10$5rMcXmTxuoQibO.RvqX6B.gEV0mxHjoZO8Sqx0JoFuFAddV2fYAX2');
insert into usuarios (id_user, id_roles, user_name, email, user_password) values (nextval('seq_usuario'), 2, 'MEMBER', 'member@gmail.com', '$2a$10$5rMcXmTxuoQibO.RvqX6B.gEV0mxHjoZO8Sqx0JoFuFAddV2fYAX2');
insert into usuarios (id_user, id_roles, user_name, email, user_password) values (nextval('seq_usuario'), 3, 'ADMIN', 'admin@gmail.com', '$2a$10$5rMcXmTxuoQibO.RvqX6B.gEV0mxHjoZO8Sqx0JoFuFAddV2fYAX2');

-------------------------------------------------------------------------------

-- INSERTS SPRINT'S

insert into sprints (id_sprint, title, start_date, end_date, status) values (nextval('seq_sprint'), 'Definição de projetos com o cliente da C&A', '2022-08-21 17:52:00', '2022-08-22 18:00:00', 'IN_PROGRESS');

insert into sprints (id_sprint, title, start_date, end_date, status) values (nextval('seq_sprint'), 'Definição das regras do Projeto A do cliente Sicredi', '2022-08-21 11:45:00', '2022-08-13 12:00:00', 'IN_PROGRESS');

insert into sprints (id_sprint, title, start_date, end_date, status) values (nextval('seq_sprint'), 'Levantamento e correções do projeto C do cliente Subway', '2022-08-21 13:25:00', '2022-08-14 13:50:00', 'IN_PROGRESS');

insert into sprints (id_sprint, title, start_date, end_date, status) values (nextval('seq_sprint'), 'Refatoração de códigos do Projeto B', '2022-08-21 10:00:00', '2022-08-21 10:32:00', 'IN_PROGRESS');

insert into sprints (id_sprint, title, start_date, end_date, status) values (nextval('seq_sprint'), 'Reunião com novo cliente ChinaInBox','2022-08-21 14:45:00', '2022-08-21 14:50:00', 'IN_PROGRESS');

-- INSERTS RETROSPECTIVES

insert into retrospectivas (id_retrospective, id_sprint, title, occurred_date, status) values (nextval('seq_retrospectiva'), 1, 'Divisão das equipes em trios', '2022-08-12 17:53:12', 'CREATE');
insert into retrospectivas (id_retrospective, id_sprint, title, occurred_date, status) values (nextval('seq_retrospectiva'), 2, 'Divisão dos trabalhos semanais', '2022-08-13 11:53:12', 'CREATE');
insert into retrospectivas (id_retrospective, id_sprint, title, occurred_date, status) values (nextval('seq_retrospectiva'), 3, 'Separação dos códigos', '2022-08-14 13:30:12', 'CREATE');
insert into retrospectivas (id_retrospective, id_sprint, title, occurred_date, status) values (nextval('seq_retrospectiva'), 4, 'Relação de entidades do projeto', '2022-08-15 10:05:12', 'CREATE');
insert into retrospectivas (id_retrospective, id_sprint, title, occurred_date, status) values (nextval('seq_retrospectiva'), 1, 'Repasse de tarefas', '2022-08-16 14:47:12', 'CREATE');

-- INSERTS KUDO BOXES

insert into kudo_boxs (id_kudo_box, id_sprint, title, end_date, status) values (nextval('seq_kudobox'), 1, 'Melhorias', '2022-08-12 17:54:12', 'CREATE');
insert into kudo_boxs (id_kudo_box, id_sprint, title, end_date, status) values (nextval('seq_kudobox'), 2, 'Observações', '2022-08-12 11:55:12', 'IN_PROGRESS');
insert into kudo_boxs (id_kudo_box, id_sprint, title, end_date, status) values (nextval('seq_kudobox'), 3, 'Melhorias', '2022-08-12 13:31:15', 'CREATE');
insert into kudo_boxs (id_kudo_box, id_sprint, title, end_date, status) values (nextval('seq_kudobox'), 4, 'Melhorias', '2022-08-12 10:07:12', 'CREATE');
