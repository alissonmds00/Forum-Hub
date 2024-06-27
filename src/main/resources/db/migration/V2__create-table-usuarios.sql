create table usuarios(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(50) not null,
    senha varchar(50) not null,
    primary key(id)
)