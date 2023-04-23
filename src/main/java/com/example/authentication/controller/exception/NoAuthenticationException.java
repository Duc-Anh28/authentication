package com.example.authentication.controller.exception;

import org.springframework.security.core.AuthenticationException;

public class NoAuthenticationException extends AuthenticationException {
    public NoAuthenticationException(String msg) {
        super(msg);
    }
}
