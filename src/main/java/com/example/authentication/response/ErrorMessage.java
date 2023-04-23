package com.example.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String code;
    private String title;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MethodArgumentNotValidResponse {
        private String field;
        private String message;
    }
}
