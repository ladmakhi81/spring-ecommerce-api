package com.codyproo.ecommerce.common.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${app.swagger.title}")
    private String swaggerTitle;

    @Value("${app.swagger.version}")
    private String swaggerVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        var securitySchema = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("bearerAuth", securitySchema)
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(
                        new Info()
                                .title(swaggerTitle)
                                .version(swaggerVersion)
                );
    }
}
