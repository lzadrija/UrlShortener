package com.lzadrija.url.registration;

import com.lzadrija.BaseTest;
import javax.servlet.http.HttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;

public class ServerAddressFactoryTest extends BaseTest {

    @Mock
    private HttpServletRequest request;
    @Autowired
    private ServerAddressFactory factory;

    @Test
    public void whenGivenHttpRequestGetServerAddress() {

        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);

        String serverAddress = factory.create(request);

        assertThat(serverAddress).isNotNull();
        assertThat(serverAddress).isEqualTo("http://localhost:8080/");
    }

}
