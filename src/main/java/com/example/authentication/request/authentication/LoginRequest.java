package com.example.authentication.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "{email.not-blank}")
    private String email;
    @NotBlank(message = "{password.not-blank}")
    private String password;
}
