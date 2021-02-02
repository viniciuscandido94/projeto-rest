package br.com.rest.projeto.config;

import com.google.common.base.Predicates;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        Parameter parameter = new ParameterBuilder()
                .name("Authorization")
                .description("Authorization Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .defaultValue("Bearer ${token value}")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Doc ProjetoRest")
                .globalOperationParameters(Collections.singletonList(parameter))
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.rest.projeto"))
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/usuario/create")))
                .paths(Predicates.not(PathSelectors.regex("/servicos/download/projeto/")))
                .paths(Predicates.not(PathSelectors.ant("/oauth/*")))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Projeto Rest",
                "Doc API Projeto Rest",
                "1.0",
                "Terms of Service",
                new Contact("Vinicius", "",""),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );
        return apiInfo;
    }

}
