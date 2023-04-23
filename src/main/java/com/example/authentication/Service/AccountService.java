package com.example.authentication.Service;

import com.example.authentication.entity.Account;
import com.example.authentication.request.authentication.LoginRequest;
import com.example.authentication.request.authentication.SignupRequest;
import com.example.authentication.response.authentication.AuthenticationResponse;

public interface AccountService {
    SignupRequest signupAccount(SignupRequest request);
    AuthenticationResponse login(LoginRequest form);

    Account getAccountByEmail(String email, String fieldName);
}
