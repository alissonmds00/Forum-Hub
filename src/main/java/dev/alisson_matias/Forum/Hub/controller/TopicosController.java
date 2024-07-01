package dev.alisson_matias.Forum.Hub.controller;

import dev.alisson_matias.Forum.Hub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
