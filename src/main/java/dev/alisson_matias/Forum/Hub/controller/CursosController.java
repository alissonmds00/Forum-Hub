package dev.alisson_matias.Forum.Hub.controller;

import dev.alisson_matias.Forum.Hub.domain.curso.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursos")
public class CursosController {

    @Autowired
    private RegistrarCurso registrarCurso;

    @Autowired
    private CursoRepository repository;

    @PostMapping()
    @Transactional
    public ResponseEntity cadastrarCurso(@RequestBody @Valid DadosCadastramentoCurso dados) {
        System.out.println(dados);
        var curso = registrarCurso.registrarNovoCurso(dados);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCurso>> listarPacientesAtivos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagina)  {
        var cursosPaginacao = repository.findAll(pagina);
        var cursosDto = cursosPaginacao.stream()
                .map(DadosDetalhamentoCurso::new)
                .collect(Collectors.toList());
        var cursos = new PageImpl<>(cursosDto, pagina, cursosPaginacao.getTotalElements());
        return ResponseEntity.ok(cursos);
    }
}
