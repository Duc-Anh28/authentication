package com.example.authentication.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuccessResponse<T> extends AbstractResponse {
    private T data;
    public SuccessResponse(int status, String message, T data) {
        super(status, false, message);
        this.data = data;
    }

    public SuccessResponse(T data) {
        super(HttpStatus.OK.value(), false, null);
        this.data = data;
    }
    public SuccessResponse() {
        super(HttpStatus.OK.value(), false, null);
    }
}
