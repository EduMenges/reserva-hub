package com.reservahub.backend.app.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handlAccessDeniedException(AccessDeniedException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleAuthenticationException(BadCredentialsException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(InvalidEventDurationException.class)
    public ResponseEntity<Object> handleInvalidEventDurationException(InvalidEventDurationException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Object> handleInvalidDateException(InvalidDateException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(RoomAlreadyReservedException.class)
    public ResponseEntity<Object> handleRoomAlreadyReservedException(RoomAlreadyReservedException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Object> invalidDataAccessApiUsageExceptionException(InvalidDataAccessApiUsageException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getStatus());

    }

}