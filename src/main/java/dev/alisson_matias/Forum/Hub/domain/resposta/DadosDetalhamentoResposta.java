package dev.alisson_matias.Forum.Hub.domain.resposta;

import dev.alisson_matias.Forum.Hub.infra.utils.FormatadorData;

public record DadosDetalhamentoResposta(Long id, String conteudo, Long topico_id, String dataDeCriacao, String autor,
                                        String solucao) {
    public DadosDetalhamentoResposta(Resposta dados) {
        this(dados.getId(), dados.getConteudo(), dados.getTopico().getId(), FormatadorData.formatarDataPadraoBrasil(dados.getDataDeCriacao()), dados.getAutor().getNome(), dados.getSolucao());
    }
}
