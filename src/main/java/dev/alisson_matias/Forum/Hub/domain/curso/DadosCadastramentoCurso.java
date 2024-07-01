package dev.alisson_matias.Forum.Hub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastramentoCurso(
        @NotBlank String nome,
        @NotNull Categoria categoria) {
}
