package com.example.authentication.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotBlank(message = "{field-require}")
    private String name;
    @NotBlank(message = "{field-require}")
    private String email;
    @NotBlank(message = "{field-require}")
    private String password;
    @NonNull
    private List<String> roles;
}
