package com.example.authentication.response.account;

import com.example.authentication.entity.Account;
import com.example.authentication.response.BaseEntityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String name;
    private String email;
    private Boolean isActive;
    private List<BaseEntityResponse> roles;

    public AccountResponse(Account accountSave) {
        this.id = accountSave.getId();
        this.name = accountSave.getName();
        this.email = accountSave.getEmail();
        this.isActive = accountSave.getIsActive();
        this.roles = accountSave.getRoles().stream().map(BaseEntityResponse::new).collect(Collectors.toList());
    }
}
