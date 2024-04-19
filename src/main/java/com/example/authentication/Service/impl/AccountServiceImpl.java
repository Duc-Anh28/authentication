package com.example.authentication.Service.impl;

import com.example.authentication.Service.AccountService;
import com.example.authentication.controller.exception.ApplicationException;
import com.example.authentication.controller.exception.ArgumentException;
import com.example.authentication.entity.Account;
import com.example.authentication.repository.AccountRepository;
import com.example.authentication.repository.RoleRepository;
import com.example.authentication.request.account.AccountFilterRequest;
import com.example.authentication.request.account.AccountUpdateRequest;
import com.example.authentication.request.account.CreateAccountRequest;
import com.example.authentication.response.PageResponse;
import com.example.authentication.response.account.AccountResponse;
import com.example.authentication.type.OrderByType;
import com.example.authentication.type.SortByFieldType;
import com.example.authentication.util.CommonPage;
import com.example.authentication.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Account getAccountSecurityByEmail(String email, String fieldName) {
        var account = accountRepository.findAccountByEmail(email);
        if (account.isEmpty()) {
            throw new ArgumentException(fieldName, "username.notfound");
        }
        Hibernate.initialize(account.get().getRoles());
        return account.get();
    }

    @Override
    @Transactional
    public Account getAccountById(Long accountId, String fieldName) {
        var accountOtp = accountRepository.findById(accountId);
        if (accountOtp.isEmpty()) {
            throw new ArgumentException(fieldName, "username.notfound");
        }
        return accountOtp.get();
    }

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        var account = new Account(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        if (CommonUtil.isNotEmpty(request.getRoles())) {
            var roles = roleRepository.listRoleByNames(request.getRoles());
            account.setRoles(roles);
        }
        try {
            var accountSave = accountRepository.save(account);
            return new AccountResponse(accountSave);
        } catch (Exception e) {
            throw new ApplicationException("account.create-error");
        }
    }

    @Override
    public Account getAccountByEmail(String email, String fieldName) {
        var account = accountRepository.findAccountByEmail(email);
        if (account.isEmpty()) {
            throw new ArgumentException(fieldName, "username.notfound");
        }
        return account.get();
    }

    @Override
    @Transactional
    public Account updateAccount(Long accountId, AccountUpdateRequest request) {
        var account = this.getAccountById(accountId, "id");
        account.setName(request.getName());
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new ApplicationException("account.update-error");
        }

    }

    @Override
    public AccountResponse getAccountDetail(Long accountId) {
        var account = this.getAccountById(accountId, "accountId");
        return new AccountResponse(account);
    }

    @Override
    public PageResponse<AccountResponse> getListAccount(AccountFilterRequest request) {
        var sort = this.functionSortAccount(request.getOrderByType(), request.getSortByType());
        Pageable pageable = CommonPage.pageWithSort(request.getPageNum(), request.getPageSize(), sort);
        Page<Account> lisAccount = accountRepository.getListAccount(request, pageable);
        return new PageResponse<>(lisAccount.map(AccountResponse::new));
    }

    public Sort functionSortAccount(OrderByType orderBy, SortByFieldType sortBy) {
        Sort sort;
        switch (orderBy) {
            case ASC -> {
                switch (sortBy) {
                    case CREATED_AT -> {
                        sort = Sort.by(Sort.Direction.ASC, SortByFieldType.CREATED_AT.getName());
                    }
                    default -> {
                        sort = Sort.by(Sort.Direction.ASC, SortByFieldType.MODIFIED_AT.getName());
                    }
                }
            }
            default -> {
                switch (sortBy) {
                    case CREATED_AT -> {
                        sort = Sort.by(Sort.Direction.DESC, SortByFieldType.CREATED_AT.getName());
                    }
                    default -> {
                        sort = Sort.by(Sort.Direction.DESC, SortByFieldType.MODIFIED_AT.getName());
                    }
                }
            }
        }
        return sort;
    }
}
