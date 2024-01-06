package com.reservahub.backend.app.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorMessage {

    private HttpStatus status;
    private List<String> errors;

    // Constructor for a single error
    public ApiErrorMessage(HttpStatus status, String error) {
        this.status = status;
        this.errors = Arrays.asList(error);
    }
}