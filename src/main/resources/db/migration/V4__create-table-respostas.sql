create table respostas(
    id bigint not null auto_increment,
    conteudo varchar(300) not null unique,
    autor_id bigint not null,
    data_de_criacao TIMESTAMP not null,
    topico_id bigint,
    solucao varchar(100),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE,
    primary key(id)
)