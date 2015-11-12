package com.lzadrija.help.config;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@PropertySource("classpath:swagger.properties")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${springfox.documentation.swagger.v2.path}")
    private String swagger2Endpoint;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .useDefaultResponseMessages(false)
                .directModelSubstitute(ModelAndView.class, Void.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .securitySchemes(newArrayList(auth()))
                .securityContexts(newArrayList(securityContext()))
                .apiInfo(apiInfo());
    }

    private Predicate<String> paths() {
        return or(
                regex("/account.*"),
                regex("/register.*"),
                regex("/statistic.*"),
                regex("/.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("URL Shortener API")
                .description("Simple REST API for URL shortening")
                .contact("lucija.zadrija@gmail.com")
                .version("1.0.0")
                .build();
    }

    private BasicAuth auth() {
        return new BasicAuth("basicAccountId");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .forPaths(or(regex("/register.*"),
                             regex("/statistic.*")))
                .build();
    }

}
