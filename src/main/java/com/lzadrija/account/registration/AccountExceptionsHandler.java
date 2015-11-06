package com.lzadrija.account.registration;

import static org.springframework.http.HttpStatus.CONFLICT;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.lzadrija.account"})
public class AccountExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(AccountRegistrationException.class)
    public ResponseEntity<AccountRegistration> accountRegistrationExceptionHandler(AccountRegistrationException ex) {

        AccountRegistration body = new AccountRegistration(ex.getMessage(), false);
        return ResponseEntity.status(CONFLICT).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AccountRegistration> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        String description = createDescriptionsForInvalidFields(ex);
        AccountRegistration body = new AccountRegistration(description, false);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(body);
    }

    private String createDescriptionsForInvalidFields(MethodArgumentNotValidException ex) {

        StringBuilder description = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach((FieldError fieldError) -> {
            description.append(createDescriptionForInvalidField(fieldError)).append(" ");
        });
        return description.toString().trim();
    }

    private String createDescriptionForInvalidField(FieldError e) {
        return String.format("[%s = \"%s\", %s]", e.getField(), e.getRejectedValue(), e.getDefaultMessage());
    }

}
