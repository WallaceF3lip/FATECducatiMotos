create database db_DucatiMotos;

use db_DucatiMotos;

create table Motos(
	ID int primary key auto_increment NOT NULL,
	Modelo varchar(50) NOT NULL, 
	Cor varchar(25) NOT NULL, 
	Ano int NOT NULL, 
	Cilindrada int NOT NULL, 
	Preco double NOT NULL
);

select * from Motos;

insert into db_ducatimotos.motos (Modelo ,Cor ,Ano ,Cilindrada ,Preco )
values('Panigale V2', 'Vermelho', 2026, 1000, 100000.0)