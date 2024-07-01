package dev.alisson_matias.Forum.Hub.domain.usuario;

public record DadosDetalhamentoUsuario(Long id, String nome, String email0) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
