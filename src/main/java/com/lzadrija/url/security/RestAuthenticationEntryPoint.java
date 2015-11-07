package com.lzadrija.url.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzadrija.ResultDescription;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        createCustomAuthenticationErrorResponse(response, ex);
    }

    private void createCustomAuthenticationErrorResponse(HttpServletResponse response, AuthenticationException ex) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), new ResultDescription(ex.getMessage(), false));
    }
}
