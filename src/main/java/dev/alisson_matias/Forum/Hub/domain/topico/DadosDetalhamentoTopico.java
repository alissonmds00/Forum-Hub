package dev.alisson_matias.Forum.Hub.domain.topico;

import dev.alisson_matias.Forum.Hub.infra.utils.FormatadorData;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, String dataDeCriacao, String autor, String curso) {
    public DadosDetalhamentoTopico(Topico dados) {
        this(dados.getId(), dados.getTitulo(), dados.getMensagem(), FormatadorData.formatarDataPadraoBrasil(dados.getDataDeCriacao()), dados.getAutor().getNome(), dados.getCurso().getNome());
    }
}
