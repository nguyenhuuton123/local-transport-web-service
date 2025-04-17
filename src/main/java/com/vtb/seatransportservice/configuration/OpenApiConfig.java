package com.vtb.seatransportservice.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${springdoc.info.title}")
    private String title;

    @Value("${springdoc.info.description}")
    private String description;

    @Value("${springdoc.info.version}")
    private String version;

    @Value("${springdoc.info.contact.name}")
    private String contactName;

    @Value("${springdoc.info.contact.email}")
    private String contactEmail;

    @Value("${springdoc.info.contact.url}")
    private String contactUrl;

    @Value("${springdoc.security.key}")
    private String securityKey;

    @Value("${springdoc.components.securitySchemes.cookieAuth.name}")
    private String securitySchemeName;

    @Value("${springdoc.security.requirement}")
    private String securitySecurityRequirement;

    @Value("${springdoc.components.securitySchemes.cookieAuth.description}")
    private String securitySchemeDescription;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)
                                .url(contactUrl)))
                .addSecurityItem(new SecurityRequirement().addList(securitySecurityRequirement))
                .components(new Components()
                        .addSecuritySchemes(securityKey, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name(securitySchemeName)
                                .description(securitySchemeDescription)));
    }

}