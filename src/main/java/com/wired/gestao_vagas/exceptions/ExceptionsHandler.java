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

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

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
        String message = "Invalid format error";
        String fieldName = "";

        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            if (!jme.getPath().isEmpty()) {
                fieldName = jme.getPath().get(0).getFieldName();
            }

            if (ex.getMessage().contains("java.math.BigDecimal")) {
                message = "Invalid number format. Use only numbers and decimal point (example: 1000.00)";
            } else if (ex.getMessage().contains("java.time.LocalDate")) {
                message = "Invalid date format. Use format yyyy-MM-dd (example: 2024-03-20)";
            } else if (ex.getMessage().contains("java.time.LocalDateTime")) {
                message = "Invalid date and time format. Use format yyyy-MM-dd'T'HH:mm:ss (example: 2024-03-20T14:30:00)";
            } else if (ex.getMessage().contains("java.lang.Boolean")) {
                message = "Invalid boolean format. Use 'true' or 'false'";
            } else if (ex.getMessage().contains("java.lang.Integer")) {
                message = "Invalid integer number format. Use only integer numbers";
            } else if (ex.getMessage().contains("java.lang.Long")) {
                message = "Invalid long integer number format. Use only integer numbers";
            } else if (ex.getMessage().contains("java.lang.Double")) {
                message = "Invalid decimal number format. Use point as decimal separator";
            } else if (ex.getMessage().contains("java.util.UUID")) {
                message = "Invalid UUID format";
            } else if (ex.getMessage().contains("com.fasterxml.jackson.databind.exc.InvalidFormatException")) {
                message = "Provided value is not valid for the expected data type";
            }
        } else if (cause instanceof JsonParseException) {
            message = "JSON mal formatado. Verifique a sintaxe do JSON enviado";
        }

        ErrorMessageDTO error = new ErrorMessageDTO(fieldName, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageDTO(ex.getField(), ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        String message = "Internal server error";

        if (ex instanceof IllegalArgumentException) {
            message = "Invalid argument: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else if (ex instanceof IllegalStateException) {
            message = "Invalid application state: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else if (ex instanceof SecurityException) {
            message = "Security error: access denied";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        } else if (ex instanceof UnsupportedOperationException) {
            message = "Unsupported operation";
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(message);
        } else if (ex instanceof NullPointerException) {
            message = "Null reference error";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Access denied: you do not have permission to perform this operation");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("HTTP method not supported for this endpoint");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getParameterName() + " Required parameter not provided");
    }
}
