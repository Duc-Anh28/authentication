package com.example.authentication.request.authentication;

import com.example.authentication.entity.Account;
import com.example.authentication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private Long accountId;
    private String email;
    private String password;
    @NonNull
    private List<String> roles;

    public SignupRequest(Account account) {
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.roles = account.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }
}
