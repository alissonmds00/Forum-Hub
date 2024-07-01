create table topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensagem varchar(300) not null unique,
    id_autor bigint not null,
    data_de_criacao TIMESTAMP not null,
    status varchar(100) not null,
    id_curso bigint not null,
    FOREIGN KEY (id_curso) REFERENCES cursos(id),
    FOREIGN KEY (id_autor) REFERENCES usuarios(id),
    primary key(id)
)