package dev.alisson_matias.Forum.Hub.controller;

import dev.alisson_matias.Forum.Hub.domain.resposta.DadosCadastramentoResposta;
import dev.alisson_matias.Forum.Hub.domain.resposta.DadosDetalhamentoResposta;
import dev.alisson_matias.Forum.Hub.domain.resposta.RegistrarResposta;
import dev.alisson_matias.Forum.Hub.domain.resposta.RespostaRepository;
import dev.alisson_matias.Forum.Hub.domain.topico.*;
import dev.alisson_matias.Forum.Hub.infra.exception.ValidacaoException;
import dev.alisson_matias.Forum.Hub.infra.security.DadosTokenJWT;
import dev.alisson_matias.Forum.Hub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/topicos")
public class TopicosController {
    private final TopicoRepository repository;
    private final RegistroDeTopico registroDeTopico;
    private final TokenService tokenService;
    private final RespostaRepository respostaRepository;
    private final RegistrarResposta registroDeResposta;
    private final ArquivamentoDeTopico arquivamentoDeTopico;

    @Autowired
    public TopicosController(TopicoRepository repository, RegistroDeTopico registroDeTopico, TokenService tokenService, RespostaRepository respostaRepository, RegistrarResposta registroDeResposta, ArquivamentoDeTopico arquivamentoDeTopico) {
        this.repository = repository;
        this.registroDeTopico = registroDeTopico;
        this.respostaRepository = respostaRepository;
        this.tokenService = tokenService;
        this.registroDeResposta = registroDeResposta;
        this.arquivamentoDeTopico = arquivamentoDeTopico;
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirTopico(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        var emailDoSolicitante = tokenService.getSubject(token);
        arquivamentoDeTopico.arquivar(id, emailDoSolicitante);
        return ResponseEntity.noContent().build();
    }

    // Respostas

    @GetMapping("{idTopico}/respostas")
    public ResponseEntity<Page<DadosDetalhamentoResposta>> obterRespostasDoTopico(@PageableDefault Pageable page, @PathVariable Long idTopico) {
        var respostas = respostaRepository.buscarRespostasDoTopico(page, idTopico).map(DadosDetalhamentoResposta::new);
        System.out.println(respostas);
        return ResponseEntity.ok(respostas);
    }

    @PostMapping("{idTopico}/respostas")
    @Transactional
    public ResponseEntity<DadosDetalhamentoResposta> registrarResposta(@PathVariable Long idTopico, @RequestBody DadosCadastramentoResposta dados, UriComponentsBuilder uriBuilder, @RequestHeader("Authorization") String token) {
        var resposta = registroDeResposta.registrarResposta(idTopico, dados.conteudo(), token);
        var uri = uriBuilder.path("/topicos/{idTopico}/resposta{idResposta}").buildAndExpand(idTopico, resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

}
