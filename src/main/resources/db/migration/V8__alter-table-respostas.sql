alter table respostas add ativo tinyint not null;
update respostas set ativo = 1;