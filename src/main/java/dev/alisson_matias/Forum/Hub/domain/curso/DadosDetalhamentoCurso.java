package dev.alisson_matias.Forum.Hub.domain.curso;

public record DadosDetalhamentoCurso(Long id, String nome, String categoria) {
    public DadosDetalhamentoCurso(Curso dados) {
        this(dados.getId(), dados.getNome(), dados.getCategoria());
    }
}
