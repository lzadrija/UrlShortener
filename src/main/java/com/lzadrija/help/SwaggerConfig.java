package com.lzadrija.help;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
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
        return new Docket(DocumentationType.SPRING_WEB)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
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
}
