package com.example.authentication.controller.exception;

import com.example.authentication.response.MethodArgumentNotValidResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListMethodArgumentNotValidException extends RuntimeException{
    List<MethodArgumentNotValidResponse> listError;

    public ListMethodArgumentNotValidException(List<MethodArgumentNotValidResponse> listError) {
        this.listError = listError;
    }
}
