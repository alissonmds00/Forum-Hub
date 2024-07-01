package dev.alisson_matias.Forum.Hub.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Perfil")
@Table(name = "perfis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Perfil(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
    }
}
