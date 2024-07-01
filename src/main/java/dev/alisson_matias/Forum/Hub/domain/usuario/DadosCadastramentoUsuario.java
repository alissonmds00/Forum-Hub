package dev.alisson_matias.Forum.Hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastramentoUsuario(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "\\w{8}") String senha) {
}
