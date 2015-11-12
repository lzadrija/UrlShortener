package com.lzadrija.help;

import com.lzadrija.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;

public class Swagger2MarkupTest extends BaseTest {

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

        this.mockMvc.perform(get("/helpjson")
                .accept(APPLICATION_JSON))
                .andDo(Swagger2MarkupResultHandler
                        .outputDirectory("src/docs/asciidoc/generated")
                        .build())
                .andExpect(status().isOk());
    }

}
