alter table topicos add ativo tinyint not null;
update topicos set ativo = 1;