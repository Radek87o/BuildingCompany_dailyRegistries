drop schema if exists `kierownikbudowy-2019-01-21`;

create schema `kierownikbudowy-2019-01-21`;

use `kierownikbudowy-2019-01-21`;

drop table if exists `users`;

create table `users` 
(
	`id` int(11) not null auto_increment,
    `username` varchar(40) not null,
    `password` char(80) not null,
    `first_name` nvarchar(40) not null,
    `last_name` nvarchar(40) not null,
    primary key(`id`),
    unique key(`first_name`,`last_name`)
)engine=InnoDB auto_increment=1 default charset=utf8;

#password - 'admin123'
insert into `users` (username, password, first_name, last_name) 
values ('jan.kowalski', '$2a$04$luIgEKrYkeFTwpDrvw9p7up10m0FWeMu9t/hTcIaTy5OYa/gCcDhW', 'Jan', 'Kowalski');

drop table if exists `role`;

create table `role`
(
	`id` int(11 )not null auto_increment,
    `name` varchar(40) not null,
    primary key (`id`),
    unique key `role_name`(`name`)
)engine=InnoDB, auto_increment=1, default charset=utf8;

insert into `role`(name) values ('ROLE_ADMIN'),('ROLE_MANAGER');

drop table if exists `users_role`;

create table `users_role`
(
	`user_id` int(11) not null,
    `role_id` int(11) not null,
    primary key(`user_id`,`role_id`),
    constraint `FK_user_id_01` foreign key(`user_id`) references `users` (`id`) on update no action on delete no action,
    constraint `FK_role_id_01` foreign key(`role_id`) references `role`(`id`) on update no action on delete no action
)engine=InnoDB, auto_increment=1, default charset=utf8;

insert into `users_role` (user_id, role_id) values ('1','1'),('1','2');

drop table if exists `projekt`;

create table `projekt` 
(
	`id_projektu` int(11) not null auto_increment,
    `nazwa_projektu` nvarchar(80) not null,
    `status_projektu` tinyint(1) not null,
    `user_id` int(11) default null,
    primary key(`id_projektu`),
    unique key (`nazwa_projektu`),
    key `FK_user_id_idx` (`user_id`),
    constraint `FK_user_id_idx` foreign key (`user_id`) references `users`(`id`) on delete no action on update no action
)engine=InnoDB auto_increment=1 default charset=utf8;

drop table if exists `pracownik`;

create table `pracownik`
(
	`id_pracownika` int(11) not null auto_increment,
    `imie` nvarchar(40) not null,
    `nazwisko` nvarchar(40) not null,
    primary key(`id_pracownika`),
    unique key (`imie`,`nazwisko`)
)engine=InnoDB auto_increment=1 default charset=utf8;

drop table if exists `projekt_pracownik`;

create table `projekt_pracownik`
(
	`projekt_id` int(11) not null,
    `pracownik_id` int(11) not null,
    primary key(`projekt_id`, `pracownik_id`),
    constraint `FK_projekt_idx` foreign key (`projekt_id`) references `projekt` (`id_projektu`) on delete no action on update no action,
    constraint `FK_pracownik_idx` foreign key (`pracownik_id`) references `pracownik` (`id_pracownika`) on delete no action on update no action
)engine=InnoDB default charset=utf8;

drop table if exists `rejestr`;

drop table if exists `nocleg`;

create table `nocleg`
(
	`id_noclegu` int(11) not null auto_increment,
    `nazwa_noclegu` nvarchar(80) not null,
    primary key (`id_noclegu`),
    unique key `nocleg_name` (`nazwa_noclegu`)
)engine=InnoDB auto_increment=1 default charset=utf8;

drop table if exists `catering`;

create table `catering`
(
	`id_catering` int(11) not null auto_increment,
    `nazwa_catering` nvarchar(80) not null,
    primary key (`id_catering`),
    unique key `catering_name`(`nazwa_catering`)
)engine=InnoDB auto_increment=1 default charset=utf8;

create table `rejestr`
(
	`id_rejestr` int(11) not null auto_increment,
    `data` date not null,
    `projekt_id` int(11) not null,
    `pracownik_id` int(11) not null,
    `czas_pracy` int(11) default null,
    `nieobecnosc` nvarchar(80) default null,
    `catering_id` int(11) default null,
    `nocleg_id` int(11) default null,
    `data_wprowadzenia` datetime not null,
    primary key (`id_rejestr`),
    unique key `rejestr_name` (`data`, `pracownik_id`),
    key `FK_projekt_rejestr_idx` (`projekt_id`),
    constraint `FK_projekt_rejestr_idx` foreign key (`projekt_id`) references `pracownik` (`id_pracownika`) on update no action on delete no action,
	key `FK_pracownik_rejestr_idx` (`pracownik_id`),
    constraint `FK_pracownik_rejestr_idx` foreign key (`pracownik_id`) references `pracownik` (`id_pracownika`) on update no action on delete no action,
    key `FK_nocleg_rejestr_idx` (`nocleg_id`),
    constraint `FK_nocleg_rejestr_idx` foreign key (`nocleg_id`) references `nocleg` (`id_noclegu`) on update no action on delete no action,
    key `FK_catering_rejestr_idx` (`catering_id`),
    constraint `FK_catering_rejestr_idx` foreign key (`catering_id`) references `catering` (`id_catering`) on update no action on delete no action
)engine=InnoDB auto_increment=1 default charset=utf8;



