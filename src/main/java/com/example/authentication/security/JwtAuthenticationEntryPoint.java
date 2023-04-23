package com.example.authentication.security;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageSource messageSource;

    public JwtAuthenticationEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message = messageSource.getMessage("access-denied", null, LocaleContextHolder.getLocale());
        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value() ,message);
    }
}
