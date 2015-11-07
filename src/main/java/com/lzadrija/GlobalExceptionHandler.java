package com.lzadrija;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final String DESCRIPTION_FOR_INVALID_FIELD_TEMPLATE = "[%s = \"%s\", %s]";

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultDescription> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        String description = createDescriptionsForInvalidFields(ex);
        ResultDescription body = new ResultDescription(description, false);
        return ResponseEntity.badRequest().contentType(APPLICATION_JSON).body(body);
    }

    private String createDescriptionsForInvalidFields(MethodArgumentNotValidException ex) {

        StringBuilder description = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach((FieldError fieldError) -> {
            description.append(createDescriptionForInvalidField(fieldError)).append(" ");
        });
        return description.toString().trim();
    }

    private String createDescriptionForInvalidField(FieldError e) {
        return String.format(DESCRIPTION_FOR_INVALID_FIELD_TEMPLATE, e.getField(), e.getRejectedValue(), e.getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultDescription> exceptionHandler(Exception ex) throws Exception {

        ResultDescription body = new ResultDescription(ex.getMessage(), false);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(APPLICATION_JSON).body(body);
    }

}
