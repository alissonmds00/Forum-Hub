package dev.alisson_matias.Forum.Hub.domain.topico;

import dev.alisson_matias.Forum.Hub.domain.curso.CursoRepository;
import dev.alisson_matias.Forum.Hub.domain.usuario.UsuarioRepository;
import dev.alisson_matias.Forum.Hub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroDeTopico {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public Topico criarNovoTopico(DadosCadastramentoTopico dados) {
        if (cursoRepository.getReferenceById(dados.idCurso()) == null) {
            throw new ValidacaoException("Não foi possível encontrar o curso informado");
        }
        if (usuarioRepository.getReferenceById(dados.idAutor()) == null) {
            throw new ValidacaoException("O usuário não é válido");
        }
        var curso = cursoRepository.getReferenceById(dados.idCurso());
        var autor = usuarioRepository.getReferenceById(dados.idAutor());
        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);
        return topico;
    }

}
