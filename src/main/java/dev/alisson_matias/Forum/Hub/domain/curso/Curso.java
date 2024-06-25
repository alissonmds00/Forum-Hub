package dev.alisson_matias.Forum.Hub.domain.curso;

import dev.alisson_matias.Forum.Hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "curso")
    @JoinColumn(name = "topicos_id")
    private List<Topico> topicos;
}
