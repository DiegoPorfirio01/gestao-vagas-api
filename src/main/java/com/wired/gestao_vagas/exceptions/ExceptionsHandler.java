package com.wired.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    private MessageSource messageSource;

    public ExceptionsHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ErrorMessageDTO> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = messageSource.getMessage(error, Locale.getDefault());
            errors.add(new ErrorMessageDTO(error.getField(), message));
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
