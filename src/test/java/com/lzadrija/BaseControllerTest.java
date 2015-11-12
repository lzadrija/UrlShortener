package com.lzadrija;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzadrija.exception.GlobalExceptionHandler;
import java.lang.reflect.Method;
import org.junit.Before;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

public abstract class BaseControllerTest extends BaseTest {

    private ObjectMapper jsonMapper;

    @Before
    @Override
    public void setUp() {

        super.setUp();
        jsonMapper = new ObjectMapper();
        jsonMapper.setSerializationInclusion(Include.NON_NULL);
    }

    protected StandaloneMockMvcBuilder createBasicMockMvcBuilder(Object controller) {

        return MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setHandlerExceptionResolvers(createExceptionResolver(new GlobalExceptionHandler()),
                                              new ResponseStatusExceptionResolver());
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver(Object handler) {

        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver() {

            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception ex) {

                Method m = new ExceptionHandlerMethodResolver(handler.getClass()).resolveMethod(ex);
                return new ServletInvocableHandlerMethod(handler, m);
            }
        };
        resolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        resolver.afterPropertiesSet();
        return resolver;

    }

    protected String toJson(Object value) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(value);
    }
}
