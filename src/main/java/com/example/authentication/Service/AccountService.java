package com.example.authentication.Service;

import com.example.authentication.entity.Account;
import com.example.authentication.request.account.AccountFilterRequest;
import com.example.authentication.request.account.AccountUpdateRequest;
import com.example.authentication.request.account.CreateAccountRequest;
import com.example.authentication.response.PageResponse;
import com.example.authentication.response.account.AccountResponse;

public interface AccountService {
    Account getAccountSecurityByEmail(String email, String fieldName);
    Account getAccountById(Long accountId, String fieldName);

    Account getAccountByEmail(String email, String fieldName);

    AccountResponse createAccount(CreateAccountRequest request);

    Account updateAccount(Long accountId, AccountUpdateRequest request);

    AccountResponse getAccountDetail(Long accountId);

    PageResponse<AccountResponse> getListAccount(AccountFilterRequest request);
}
