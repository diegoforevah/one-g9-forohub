package com.alura.foro_hub.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
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
}