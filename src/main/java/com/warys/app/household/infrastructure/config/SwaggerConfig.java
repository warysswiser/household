package com.warys.app.household.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.api.title}")
    private String title;
    @Value("${swagger.api.description}")
    private String description;
    @Value("${swagger.api.version}")
    private String version;
    @Value("${swagger.api.termsOfService}")
    private String termsOfService;
    @Value("${swagger.api.contact.name}")
    private String contactName;
    @Value("${swagger.api.license.name}")
    private String license;
    @Value("${swagger.api.license.url}")
    private String licenseUrl;
    @Value("${swagger.api.contact.url}")
    private String contactUrl;
    @Value("${swagger.api.contact.email}")
    private String contactEmail;
    @Value("${swagger.basePackage}")
    private String basePackage;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(title, description, version, termsOfService, getContact(), license, licenseUrl, List.of());
    }

    private Contact getContact() {
        return new Contact(contactName, contactUrl, contactEmail);
    }
}
