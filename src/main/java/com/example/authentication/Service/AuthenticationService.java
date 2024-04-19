package com.example.authentication.Service;

import com.example.authentication.request.authentication.LoginRequest;
import com.example.authentication.response.account.AccountResponse;
import com.example.authentication.response.authentication.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest form);

    AccountResponse getUserInfo();
}