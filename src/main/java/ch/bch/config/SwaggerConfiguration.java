/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2017.
 */

package ch.bch.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public static final String INTERNAL_TAG_PLAYER = "Player";
    public static final String INTERNAL_TAG_TAG = "Tag";
    public static final String INTERNAL_TAG_TASK = "Task";
    public static final String INTERNAL_TAG_GAME = "Game";

    @Bean
    public GroupedOpenApi internalApi() {
        return GroupedOpenApi.builder()
                .group("internal")
                .pathsToMatch("/admin/loggers/**", "/internal/**")
                .addOpenApiCustomizer(internalApiCustomizer())
                .build();
    }

    private OpenApiCustomizer internalApiCustomizer() {
        return api -> api
                .info(commonApiInfo()
                        .title("Prost API")
                        .description("Interne Schnittstellen für Prost API"))
                .tags(List.of(
                        new Tag().name(INTERNAL_TAG_GAME),
                        new Tag().name(INTERNAL_TAG_PLAYER),
                        new Tag().name(INTERNAL_TAG_TAG),
                        new Tag().name(INTERNAL_TAG_TASK)
                ));
    }

    private Info commonApiInfo() {
        return new Info()
                .contact(new Contact()
                        .name("Anna Emmenegger")
                        .email("anna.emmenegger.xs@gmail.com")
                        .url("bigchungus.ch"));
    }
}
