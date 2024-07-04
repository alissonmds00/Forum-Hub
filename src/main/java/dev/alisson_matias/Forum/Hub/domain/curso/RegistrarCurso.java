package dev.alisson_matias.Forum.Hub.domain.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrarCurso {
    @Autowired
    private CursoRepository repository;

    public Curso registrarNovoCurso(DadosCadastramentoCurso dados) {
        var curso = new Curso(null, dados.nome(), dados.categoria().toString());
        repository.save(curso);
        return curso;
    }
}