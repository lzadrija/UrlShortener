package com.lzadrija.url.registration;

import com.lzadrija.ResultDescription;
import com.lzadrija.url.UrlController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(assignableTypes = {UrlController.class}, basePackages = {"com.lzadrija.url"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UrlExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(UrlRegistrationException.class)
    public ResponseEntity<ResultDescription> accountRegistrationExceptionHandler(UrlRegistrationException ex) {

        ResultDescription body = new ResultDescription(ex.getMessage(), false);
        return ResponseEntity.status(CONFLICT).contentType(APPLICATION_JSON).body(body);
    }

}
