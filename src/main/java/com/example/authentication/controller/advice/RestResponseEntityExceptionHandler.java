package com.example.authentication.controller.advice;

import com.example.authentication.controller.exception.ArgumentException;
import com.example.authentication.response.ErrorsResponse;
import com.example.authentication.response.MethodArgumentNotValidResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public RestResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<ErrorsResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        ErrorsResponse<Object> response = new ErrorsResponse<>(HttpStatus.FORBIDDEN.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorsResponse<Object>> handleNotFound(NotFoundException ex) {
        ErrorsResponse<Object> response = new ErrorsResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorsResponse<List<MethodArgumentNotValidResponse>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = messageSource.getMessage("bad-request", null, LocaleContextHolder.getLocale());

        List<MethodArgumentNotValidResponse> errors = ex.getFieldErrors().stream().map(e ->
                new MethodArgumentNotValidResponse(e.getField(), e.getDefaultMessage())
        ).collect(Collectors.toList());

        ErrorsResponse<List<MethodArgumentNotValidResponse>> response = new ErrorsResponse<>(HttpStatus.BAD_REQUEST.value(), message, errors);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<ErrorsResponse<Object>> handleNotFound(AuthenticationException ex) {
        ErrorsResponse<Object> response = new ErrorsResponse<>(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ArgumentException.class})
    protected ResponseEntity<ErrorsResponse<List<MethodArgumentNotValidResponse>>> handleMethodArgumentNotValid(ArgumentException ex) {
        String message = messageSource.getMessage("bad-request", null, LocaleContextHolder.getLocale());
        try {
            var errorMessage = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
            var response = new ErrorsResponse<>(HttpStatus.BAD_REQUEST.value(), message, List.of(new MethodArgumentNotValidResponse(ex.getField(), errorMessage)));
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            var response = new ErrorsResponse<>(HttpStatus.BAD_REQUEST.value(), message, List.of(new MethodArgumentNotValidResponse(ex.getField(), ex.getMessage())));
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

}
