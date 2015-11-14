package com.lzadrija.help;

import com.lzadrija.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;

@PropertySource("classpath:swagger.properties")
public class Swagger2MarkupTest extends BaseTest {

    private static final String OUTPUT_DIR = "src/docs/asciidoc/generated";

    @Value("${springfox.documentation.swagger.v2.path}")
    private String swagger2Endpoint;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void convertSwaggerToAsciiDoc() throws Exception {

        this.mockMvc.perform(get(swagger2Endpoint)
                .accept(APPLICATION_JSON))
                .andDo(Swagger2MarkupResultHandler
                        .outputDirectory(OUTPUT_DIR)
                        .build())
                .andExpect(status().isOk());
    }

}
