package automach.profiles.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Servicio de Roles - Plataforma Autos Usados")
                        .description("Gestión de perfiles de usuario (cliente y vendedor)")
                        .version("1.0.0"));
    }
}
