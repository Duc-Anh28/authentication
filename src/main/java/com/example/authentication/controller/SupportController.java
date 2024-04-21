package com.example.authentication.controller;

import com.example.authentication.Service.SupportService;
import com.example.authentication.request.SenderEmailRequest;
import com.example.authentication.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportController {
    private final SupportService supportService;
    private final MessageSource messageSource;

    @PostMapping("/send-email")
    public ResponseEntity<SuccessResponse<String>> createFeedback(@RequestBody SenderEmailRequest request) {
        supportService.setSenderEmail(request);
        SuccessResponse<String> response = new SuccessResponse<>(
                HttpStatus.OK.value(),
                messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale()),
                null);
        return ResponseEntity.ok(response);
    }
}
