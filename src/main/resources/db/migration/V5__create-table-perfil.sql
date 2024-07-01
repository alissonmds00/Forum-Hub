create table perfis(
    id bigint not null auto_increment,
    nome varchar(50) unique,
    id_usuario bigint not null,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    primary key (id)
)