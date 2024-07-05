package dev.alisson_matias.Forum.Hub.domain.resposta;

import dev.alisson_matias.Forum.Hub.domain.topico.TopicoRepository;
import dev.alisson_matias.Forum.Hub.domain.usuario.UsuarioRepository;
import dev.alisson_matias.Forum.Hub.infra.exception.ValidacaoException;
import dev.alisson_matias.Forum.Hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrarResposta {
    private final UsuarioRepository usuarioRepository;
    private final RespostaRepository respostaRepository;
    private final TopicoRepository topicoRepository;
    private final TokenService tokenService;

    @Autowired
    public RegistrarResposta(UsuarioRepository usuarioRepository, RespostaRepository respostaRepository, TopicoRepository topicoRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.respostaRepository = respostaRepository;
        this.topicoRepository = topicoRepository;
        this.tokenService = tokenService;
    }

    public Resposta registrarResposta(Long idTopico, String mensagem, String token) {
        var topico = topicoRepository.getReferenceById(idTopico);
        if (topico == null) {
            throw new ValidacaoException("O tópico informado não existe ou está desativado.");
        }
        var autorEmail = tokenService.getSubject(token);
        var autor = usuarioRepository.findByEmail(autorEmail);
        var resposta = new Resposta(mensagem, topico, autor);
        respostaRepository.save(resposta);
        return resposta;
    }
}
