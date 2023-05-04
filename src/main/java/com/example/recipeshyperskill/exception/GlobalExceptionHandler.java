package com.example.recipeshyperskill.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RecipeNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(notFoundMap(e, request), HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> notFoundMap(Exception ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = formatter.format(new Date());
        response.put("timestamp", date);
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());
        return response;
    }
}
