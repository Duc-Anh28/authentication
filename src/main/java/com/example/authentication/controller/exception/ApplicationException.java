package com.example.authentication.controller.exception;

import com.example.authentication.response.MethodArgumentNotValidResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException{
    public ApplicationException(MethodArgumentNotValidResponse errorMethodArgument) {
        this.errorMethodArgument = errorMethodArgument;
    }
    public ApplicationException(String msgKey){
        super();
        this.msgKey = msgKey;
        this.locale = LocaleContextHolder.getLocale();
    }

    public ApplicationException(String msgKey, Object[] bindings){
        super();
        this.msgKey = msgKey;
        this.bindings = bindings;
        this.locale = LocaleContextHolder.getLocale();
    }

    public ApplicationException(String msgKey, Object[] bindings, Locale locale){
        super();
        this.msgKey = msgKey;
        this.bindings = bindings;
        this.locale = locale;
    }
    List<MethodArgumentNotValidResponse> listError;
    MethodArgumentNotValidResponse errorMethodArgument;
    private String msgKey;
    private Object[] bindings;
    private Locale locale;
}
