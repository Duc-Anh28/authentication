package com.example.authentication.Service.impl;

import com.example.authentication.Service.AuthenticationService;
import com.example.authentication.controller.exception.ArgumentException;
import com.example.authentication.helper.AuthenticationHelper;
import com.example.authentication.repository.AccountRepository;
import com.example.authentication.request.authentication.LoginRequest;
import com.example.authentication.response.account.AccountResponse;
import com.example.authentication.response.authentication.AuthenticationResponse;
import com.example.authentication.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationHelper authenticationHelper;
    private final JwtUtils jwtUtils;

    @Value("${jwt.duration}")
    private Integer duration;

    @Override
    public AuthenticationResponse login(LoginRequest form) {
        var accountOpl = accountRepository.findAccountByEmail(form.getEmail());
        if (accountOpl.isEmpty()) {
            throw new ArgumentException("email", "username.notfound");
        }
        var account = accountOpl.get();
        if (!account.getIsActive()) {
            throw new ArgumentException("email", "email.inactive");
        }
        if (!passwordEncoder.matches(form.getPassword(), account.getPassword())) {
            throw new ArgumentException(
                    "password",
                    messageSource.getMessage("password.invalid", null, LocaleContextHolder.getLocale()));
        }
        var jwt = jwtUtils.generateToken(account.getEmail(), duration * 1000);
        return new AuthenticationResponse(account, jwt);
    }

    @Override
    public AccountResponse getUserInfo() {
        return new AccountResponse(authenticationHelper.getUser());
    }

}
