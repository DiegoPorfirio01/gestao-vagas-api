package com.wired.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Erro de formato inválido";
        String fieldName = "";

        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            if (!jme.getPath().isEmpty()) {
                fieldName = jme.getPath().get(0).getFieldName();
            }

            if (ex.getMessage().contains("java.math.BigDecimal")) {
                message = "Formato de número inválido. Use apenas números e ponto decimal (exemplo: 1000.00)";
            } else if (ex.getMessage().contains("java.time.LocalDate")) {
                message = "Formato de data inválido. Use o formato yyyy-MM-dd (exemplo: 2024-03-20)";
            } else if (ex.getMessage().contains("java.time.LocalDateTime")) {
                message = "Formato de data e hora inválido. Use o formato yyyy-MM-dd'T'HH:mm:ss (exemplo: 2024-03-20T14:30:00)";
            } else if (ex.getMessage().contains("java.lang.Boolean")) {
                message = "Formato booleano inválido. Use 'true' ou 'false'";
            } else if (ex.getMessage().contains("java.lang.Integer")) {
                message = "Formato de número inteiro inválido. Use apenas números inteiros";
            } else if (ex.getMessage().contains("java.lang.Long")) {
                message = "Formato de número inteiro longo inválido. Use apenas números inteiros";
            } else if (ex.getMessage().contains("java.lang.Double")) {
                message = "Formato de número decimal inválido. Use ponto como separador decimal";
            } else if (ex.getMessage().contains("java.util.UUID")) {
                message = "Formato UUID inválido";
            } else if (ex.getMessage().contains("com.fasterxml.jackson.databind.exc.InvalidFormatException")) {
                message = "Valor fornecido não é válido para o tipo de dado esperado";
            }
        } else if (cause instanceof JsonParseException) {
            message = "JSON mal formatado. Verifique a sintaxe do JSON enviado";
        }

        ErrorMessageDTO error = new ErrorMessageDTO(fieldName, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
