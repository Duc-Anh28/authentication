package com.example.authentication.controller;

import com.example.authentication.Service.AccountService;
import com.example.authentication.request.authentication.LoginRequest;
import com.example.authentication.request.authentication.SignupRequest;
import com.example.authentication.response.SuccessResponse;
import com.example.authentication.response.authentication.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccountService accountService;
    private final MessageSource messageSource;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<SignupRequest>> signup(@Validated @RequestBody SignupRequest request) {
        var signupRequest = accountService.signupAccount(request);
        SuccessResponse<SignupRequest> response =
                new SuccessResponse<>(HttpStatus.OK.value(), null, signupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> login(@Validated @RequestBody LoginRequest form) {
        var authenResponse = accountService.login(form);
        var message = messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale());
        SuccessResponse<AuthenticationResponse> response =
                new SuccessResponse<>(HttpStatus.OK.value(), message, authenResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/signup")
    public ResponseEntity<SuccessResponse<SignupRequest>> updateAccount(@Validated @RequestBody SignupRequest request) {
        var signupRequest = accountService.signupAccount(request);
        SuccessResponse<SignupRequest> response =
                new SuccessResponse<>(HttpStatus.OK.value(), null, signupRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        return "Goodbye";
    }
}
