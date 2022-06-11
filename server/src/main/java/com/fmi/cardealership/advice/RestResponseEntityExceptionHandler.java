package com.fmi.cardealership.advice;

import com.fmi.cardealership.dto.ErrorDto;
import com.fmi.cardealership.exception.EntityNotFoundException;
import com.fmi.cardealership.exception.FileNotFoundException;
import com.fmi.cardealership.exception.FileStorageException;
import com.fmi.cardealership.exception.WrongParametersException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {EntityNotFoundException.class, FileNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            RuntimeException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorDto = new ErrorDto(message);
        return handleExceptionInternal(ex, errorDto,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = {FileStorageException.class, IllegalStateException.class, WrongParametersException.class})
    protected ResponseEntity<Object> handleStorageError(
            RuntimeException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorDto = new ErrorDto(message);
        return handleExceptionInternal(ex, errorDto,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({JwtException.class, AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorDto> handleAuthenticationException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(value
            = {WrongParametersException.class})
    protected ResponseEntity<Object> handleWrongParametersException(
            RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(ex.getMessage()));
    }
}
