package dev.alisson_matias.Forum.Hub.controller;

import dev.alisson_matias.Forum.Hub.domain.topico.DadosCadastramentoTopico;
import dev.alisson_matias.Forum.Hub.domain.topico.DadosDetalhamentoTopico;
import dev.alisson_matias.Forum.Hub.domain.topico.RegistroDeTopico;
import dev.alisson_matias.Forum.Hub.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
    @Autowired
    private TopicoRepository repository;

    @Autowired
    private RegistroDeTopico registroDeTopico;

    @PostMapping
    @Transactional
    public ResponseEntity criarTopico(@RequestBody @Valid DadosCadastramentoTopico dados, UriComponentsBuilder uriBuilder) {
        var topico = registroDeTopico.criarNovoTopico(dados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicos(@PageableDefault(size = 10) Pageable page) {
        var topicos = repository.listar10UltimosTopicos(page).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicosPorCursoEAno(@PageableDefault(size = 10)String curso, int ano, Pageable page) {
        var nomeCursoFormatado = curso.replaceAll("\\+", " ");
        var anoFormatado = Long.valueOf(ano);
        var cursos = repository.listarTopicosPorCursoEAnoDeCriacao(nomeCursoFormatado, anoFormatado, page).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarTopicoPorId(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

}
