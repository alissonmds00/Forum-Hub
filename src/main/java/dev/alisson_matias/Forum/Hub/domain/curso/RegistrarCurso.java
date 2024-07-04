package dev.alisson_matias.Forum.Hub.domain.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrarCurso {

    private final CursoRepository repository;

    @Autowired
    public RegistrarCurso(CursoRepository repository) {
        this.repository = repository;
    }

    public Curso registrarNovoCurso(DadosCadastramentoCurso dados) {
        var curso = new Curso(null, dados.nome(), dados.categoria().toString());
        repository.save(curso);
        return curso;
    }
}
