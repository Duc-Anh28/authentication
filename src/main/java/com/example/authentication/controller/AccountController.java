package com.example.authentication.controller;

import com.example.authentication.Service.AccountService;
import com.example.authentication.request.account.AccountFilterRequest;
import com.example.authentication.request.account.AccountUpdateRequest;
import com.example.authentication.request.account.CreateAccountRequest;
import com.example.authentication.response.PageResponse;
import com.example.authentication.response.SuccessResponse;
import com.example.authentication.response.account.AccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final MessageSource messageSource;

    @GetMapping("")
    public ResponseEntity<SuccessResponse<PageResponse<AccountResponse>>> getListAccount(@Validated AccountFilterRequest request) {
        var account = accountService.getListAccount(request);
        SuccessResponse<PageResponse<AccountResponse>> response = new SuccessResponse<>(HttpStatus.OK.value(), null, account);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<SuccessResponse<AccountResponse>> getAccountDetail(@PathVariable("accountId") Long accountId) {
        var account = accountService.getAccountDetail(accountId);
        SuccessResponse<AccountResponse> response = new SuccessResponse<>(HttpStatus.OK.value(), null, account);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<SuccessResponse<AccountResponse>> createAccount(@Validated @RequestBody CreateAccountRequest request) {
        var account = accountService.createAccount(request);
        var message = messageSource.getMessage(
                "account.create-success",
                new Long[]{account.getId()},
                LocaleContextHolder.getLocale()
        );
        SuccessResponse<AccountResponse> response = new SuccessResponse<>(HttpStatus.OK.value(), message, account);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> updateAccount(
            @PathVariable("id") Long id,
            @Validated @RequestBody AccountUpdateRequest request
    ) {
        var account = accountService.updateAccount(id, request);
        var message = messageSource.getMessage(
                "account.update-success",
                new Long[]{account.getId()},
                LocaleContextHolder.getLocale()
        );
        SuccessResponse<String> response = new SuccessResponse<>(HttpStatus.OK.value(), message, null);
        return ResponseEntity.ok(response);
    }

}
