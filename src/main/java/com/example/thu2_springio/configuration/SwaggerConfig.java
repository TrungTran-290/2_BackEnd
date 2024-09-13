package com.example.thu2_springio.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${open.api.title}")
    private String title;

    @Value("${open.api.version}")
    private String version;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description("This is a sample")
                        .license(new License().name("Apache License, Version 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .servers(List.of(new Server().url("http://localhost:8080/").description("this is a sample")));
    }

    @Bean
    public GroupedOpenApi publicApi1() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/{api-prefix}/admin/**")
                .build();
    }
    @Bean
    public GroupedOpenApi publicApi2() {
        return GroupedOpenApi.builder()
                .group("layout")
                .pathsToMatch("/{api-prefix}/layout/**")
                .build();
    }
    @Bean
    public GroupedOpenApi publicApi3() {
        return GroupedOpenApi.builder()
                .group("student")
                .pathsToMatch("/api/v1/student/**")
                .build();
    }
}
