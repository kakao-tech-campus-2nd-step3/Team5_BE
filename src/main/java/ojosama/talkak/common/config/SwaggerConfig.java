package ojosama.talkak.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        final String JWT = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);
        Components components = new Components().addSecuritySchemes(JWT, new SecurityScheme()
            .name(JWT)
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat(JWT)
        );
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
            .components(components);
    }

    private Info apiInfo() {
        return new Info()
            .title("Talkak API Docs")
            .description("Talkak Service Swagger UI")
            .version("1.0.0");
    }
}
