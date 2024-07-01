package dev.alisson_matias.Forum.Hub.domain.resposta;

import dev.alisson_matias.Forum.Hub.domain.topico.Topico;
import dev.alisson_matias.Forum.Hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conteudo;
    @ManyToOne
    private Topico topico;
    private LocalDateTime dataDeCriacao;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private Solucao solucao;
}
