package com.lzadrija.exception;

import com.lzadrija.ResultDescription;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MSG_TEMPLATE_FOR_INVALID_FIELD = "[%s = \"%s\", %s]";

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultDescription> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String description = createInvalidFieldsDescription(ex);
        return createResponseEntity(description, BAD_REQUEST);
    }

    private String createInvalidFieldsDescription(MethodArgumentNotValidException ex) {
        StringBuilder description = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach((FieldError fieldError) -> {
            description.append(createDescriptionForInvalidField(fieldError)).append(" ");
        });
        return description.toString().trim();
    }

    private String createDescriptionForInvalidField(FieldError e) {
        return String.format(MSG_TEMPLATE_FOR_INVALID_FIELD, e.getField(), e.getRejectedValue(), e.getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultDescription> handleGeneralException(Exception ex) throws Exception {
        return createResponseEntity(ex.getMessage(), getStatus(ex));
    }

    private HttpStatus getStatus(Exception ex) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        return (responseStatus != null) ? responseStatus.code() : INTERNAL_SERVER_ERROR;
    }

    private ResponseEntity<ResultDescription> createResponseEntity(String description, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .contentType(APPLICATION_JSON)
                .body(new ResultDescription(description, false));
    }

}
