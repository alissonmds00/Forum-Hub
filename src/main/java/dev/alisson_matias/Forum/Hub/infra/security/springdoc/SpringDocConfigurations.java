package dev.alisson_matias.Forum.Hub.infra.security.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        var mensagem = """
API Rest da aplicação Forum Hub, contendo as funcionalidades de CRUD de cursos, tópicos, respostas e gerenciamento de usuário.
Desenvolvida por Alisson Matias
OBS: para iniciar o uso e os testes, é necessário fazer o registro através da rota /registrar e em seguida, login na rota /login.
""";
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))).
                info(new Info()
                        .title("API Forum Hub").
                        description(mensagem)
                        .contact(new Contact()
                                .name("Alisson Matias")
                                .email("alissonmds00@gmail.com")));
    }
}
