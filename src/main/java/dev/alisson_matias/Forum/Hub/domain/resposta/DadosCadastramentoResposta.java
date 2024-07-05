package dev.alisson_matias.Forum.Hub.domain.resposta;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastramentoResposta(@NotBlank String conteudo) {
}
