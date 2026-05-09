package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.domain.exception.ClienteExistenteException;
import com.btg.funds.domain.exception.ClienteNoEncontradoException;
import com.btg.funds.domain.exception.CredencialesInvalidasException;
import com.btg.funds.domain.exception.FondoNoEncontradoException;
import com.btg.funds.domain.exception.SaldoInsuficienteException;
import com.btg.funds.domain.exception.SuscripcionDuplicadaException;
import com.btg.funds.domain.exception.SuscripcionNoActivaException;
import com.btg.funds.infrastructure.adapter.in.web.dto.ApiResponse;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SaldoInsuficienteException.class)
    ResponseEntity<ApiResponse<Void>> handleSaldoInsuficiente(SaldoInsuficienteException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(FondoNoEncontradoException.class)
    ResponseEntity<ApiResponse<Void>> handleFondoNoEncontrado(FondoNoEncontradoException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    ResponseEntity<ApiResponse<Void>> handleClienteNoEncontrado(ClienteNoEncontradoException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(SuscripcionDuplicadaException.class)
    ResponseEntity<ApiResponse<Void>> handleSuscripcionDuplicada(SuscripcionDuplicadaException exception) {
        return error(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(ClienteExistenteException.class)
    ResponseEntity<ApiResponse<Void>> handleClienteExistente(ClienteExistenteException exception) {
        return error(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(SuscripcionNoActivaException.class)
    ResponseEntity<ApiResponse<Void>> handleSuscripcionNoActiva(SuscripcionNoActivaException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class, CredencialesInvalidasException.class})
    ResponseEntity<ApiResponse<Void>> handleBadCredentials(RuntimeException exception) {
        return error(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException exception) {
        return error(HttpStatus.FORBIDDEN, "Acceso denegado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> fields = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage(),
                        (first, second) -> first));
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, fields, "Error de validación",
                java.time.Instant.now()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error("Error no controlado", exception);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    private ResponseEntity<ApiResponse<Void>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApiResponse.error(message));
    }
}
