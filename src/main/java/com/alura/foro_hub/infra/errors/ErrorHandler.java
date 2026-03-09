package com.alura.foro_hub.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorEnDto>> tratarError400(MethodArgumentNotValidException e) {
        List<ErrorEnDto> errores = e.getFieldErrors().stream()
                .map(ErrorEnDto::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    public record ErrorEnDto(String campo, String error) {
        public ErrorEnDto(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> tratarErrorForbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> tratarErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarError500(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getLocalizedMessage());
    }

    @ExceptionHandler(ValidacionEx.class)
    public ResponseEntity<String> tratarErrorDeValidacion(ValidacionEx e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TokenEx.class)
    public ResponseEntity<String> tratarErrorToken(TokenEx e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}