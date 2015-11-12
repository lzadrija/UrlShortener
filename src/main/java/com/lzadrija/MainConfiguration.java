package com.lzadrija;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan
public class MainConfiguration extends WebMvcConfigurerAdapter {

    private static final String DOCS_DIR = "/docs/";
    private static final String DOCS_LOCATION = "/WEB-INF" + DOCS_DIR;
    private static final String RESOURCE_HANDLER = DOCS_DIR + "**";
    private static final String DOCS_PAGE_SUFFIX = "*.html";

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix(DOCS_DIR);
        internalResourceViewResolver.setSuffix(DOCS_PAGE_SUFFIX);
        return internalResourceViewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCE_HANDLER).addResourceLocations(DOCS_LOCATION);
    }
}
