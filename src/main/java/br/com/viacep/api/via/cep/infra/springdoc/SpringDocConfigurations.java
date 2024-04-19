package br.com.viacep.api.via.cep.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Via CEP")
                        .description("API Rest com Spring para comunicação com a API aberta Via CEP")
                        .contact(new Contact()
                                .name("BackEnd")
                                .email("backend@4life.com"))
                        .license(new License()
                                .name("Via CEP")
                                .url("https://viacep.com.br/"))
                );
    }
}
