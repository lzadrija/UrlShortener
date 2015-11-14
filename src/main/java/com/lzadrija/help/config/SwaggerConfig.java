package com.lzadrija.help.config;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static com.lzadrija.help.config.SwaggerConfig.HelpProperty.CONTACT;
import static com.lzadrija.help.config.SwaggerConfig.HelpProperty.DESCRIPTION;
import static com.lzadrija.help.config.SwaggerConfig.HelpProperty.TITLE;
import static com.lzadrija.help.config.SwaggerConfig.HelpProperty.VERSION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource(value = {"classpath:swagger.properties", "classpath:help.properties"})
public class SwaggerConfig {

    @Autowired
    private Environment env;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .directModelSubstitute(ModelAndView.class, Void.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .securitySchemes(newArrayList(auth()))
                .securityContexts(newArrayList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE.getValue(env))
                .description(DESCRIPTION.getValue(env))
                .contact(CONTACT.getValue(env))
                .version(VERSION.getValue(env))
                .build();
    }

    private BasicAuth auth() {
        return new BasicAuth("basicAuth");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .forPaths(or(regex("/register.*"),
                             regex("/statistic.*")))
                .build();
    }

    enum HelpProperty {

        TITLE,
        DESCRIPTION,
        CONTACT,
        VERSION;

        String getValue(Environment env) {
            return env.getRequiredProperty(name().toLowerCase());
        }

    }

}
