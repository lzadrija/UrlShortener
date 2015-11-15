package com.lzadrija.url.registration;

import javax.servlet.http.HttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServerAddressFactoryTest {

    @Mock
    private HttpServletRequest request;

    private final ServiceAddressFactory factory = new ServiceAddressFactory();

    @Test
    public void givenHttpRequestShouldGetServerAddress() {

        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);
        when(request.getContextPath()).thenReturn("");

        String serverAddress = factory.create(request);

        assertThat(serverAddress).isNotNull();
        assertThat(serverAddress).isEqualTo("http://localhost:8080/");
    }

    @Test
    public void givenHttpRequestShouldGetServerAddressAndContextPath() {

        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8080);
        when(request.getContextPath()).thenReturn("/url-shortener-1.0.0");

        String serverAddress = factory.create(request);

        assertThat(serverAddress).isNotNull();
        assertThat(serverAddress).isEqualTo("http://localhost:8080/url-shortener-1.0.0/");
    }

}
