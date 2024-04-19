package com.example.authentication.response;

import com.example.authentication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityResponse {
    private Long id;
    private String name;
    private String email;

    public BaseEntityResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
