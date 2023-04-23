package com.example.authentication.response.authentication;

import com.example.authentication.entity.Account;
import com.example.authentication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private Long accountId;
    private String email;
    private List<String> roles;
    private String token;

    public AuthenticationResponse(Account account, String jwt) {
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.roles = account.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        this.token = jwt;
    }

}
