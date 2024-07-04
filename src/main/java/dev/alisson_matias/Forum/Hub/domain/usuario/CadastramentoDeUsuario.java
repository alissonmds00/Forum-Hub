package dev.alisson_matias.Forum.Hub.domain.usuario;

import dev.alisson_matias.Forum.Hub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CadastramentoDeUsuario {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrar(DadosCadastramentoUsuario dados) {
        String senhaCodificada = passwordEncoder.encode(dados.senha());
        var usuario = new  Usuario(dados.nome(), dados.email(), senhaCodificada);
        usuarioRepository.save(usuario);
        return usuario;
    }

}
