package com.reservahub.backend.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleAuthenticationException(BadCredentialsException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }
}
