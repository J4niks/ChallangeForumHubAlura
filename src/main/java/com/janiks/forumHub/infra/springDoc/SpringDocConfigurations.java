package com.janiks.forumHub.infra.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Forum Hub")
                        .description("Api para o challange Forum Hub alura ")
                        .contact(new Contact()
                                .name("creator")
                                .email("contato.janiksoliveira@gmail.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://github.com/J4niks/ChallangeForumHubAlura/blob/master/LICENSE")));
    }
 

}
