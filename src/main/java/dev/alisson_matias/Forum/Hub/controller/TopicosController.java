package dev.alisson_matias.Forum.Hub.controller;

import dev.alisson_matias.Forum.Hub.domain.topico.*;
import dev.alisson_matias.Forum.Hub.domain.usuario.UsuarioRepository;
import dev.alisson_matias.Forum.Hub.infra.exception.ValidacaoException;
import dev.alisson_matias.Forum.Hub.infra.security.DadosTokenJWT;
import dev.alisson_matias.Forum.Hub.infra.security.TokenService;
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
    private final TopicoRepository repository;
    private final RegistroDeTopico registroDeTopico;
    private final TokenService tokenService;

    @Autowired
    public TopicosController(TopicoRepository repository, RegistroDeTopico registroDeTopico, TokenService tokenService) {
        this.repository = repository;
        this.registroDeTopico = registroDeTopico;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> criarTopico(@RequestBody @Valid DadosCadastramentoTopico dados, UriComponentsBuilder uriBuilder, @RequestHeader("Authorization") String tokenJWT) {
        var emailAutor = tokenService.getSubject(new DadosTokenJWT(tokenJWT).token());
        var topico = registroDeTopico.criarNovoTopico(dados, emailAutor);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicos(@PageableDefault(size = 20) Pageable page) {
        var topicos = repository.listar10UltimosTopicos(page).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicosPorCursoEAno(@PageableDefault(size = 20)String curso, int ano, Pageable page) {
        var nomeCursoFormatado = curso.replaceAll("\\+", " ");
        var anoFormatado = Long.valueOf(ano);
        var cursos = repository.listarTopicosPorCursoEAnoDeCriacao(nomeCursoFormatado, anoFormatado, page).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> buscarTopicoPorId(@PathVariable Long id) {
        try {
            var topico = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
        } catch (Exception e) {
            throw new ValidacaoException("O tópico não foi encontrado");
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizarTopico(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        try {
            var topico = repository.getReferenceById(id);
            topico.atualizarDados(dados);
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
        } catch (Exception e) {
            throw new ValidacaoException("O tópico não foi encontrado.");
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> excluirTopico(@PathVariable Long id) {
        try {
            var topico = repository.getReferenceById(id);
            topico.excluirTopico();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ValidacaoException("O tópico não foi encontrado.");
        }
    }
}
