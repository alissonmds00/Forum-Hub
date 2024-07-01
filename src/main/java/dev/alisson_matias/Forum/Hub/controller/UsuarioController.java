package dev.alisson_matias.Forum.Hub.controller;

import dev.alisson_matias.Forum.Hub.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastramentoDeUsuario cadastramentoDeUsuario;


    @PostMapping
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastramentoUsuario dados, UriComponentsBuilder uriBuilder) {
        var usuario = cadastramentoDeUsuario.cadastrar(dados);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        usuarioRepository.save(usuario);
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }
}
