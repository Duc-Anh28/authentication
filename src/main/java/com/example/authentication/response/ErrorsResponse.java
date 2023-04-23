package com.example.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsResponse<T> extends AbstractResponse {
    private T errors;

    public ErrorsResponse(int status, String message, T errors) {
        super(status, true, message);
        this.errors = errors;
    }
}