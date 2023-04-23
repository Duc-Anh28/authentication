package com.example.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodArgumentNotValidResponse {
    private String field;
    private String message;
}
