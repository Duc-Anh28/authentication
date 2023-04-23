package com.example.authentication.Service;

import com.example.authentication.controller.exception.ArgumentException;
import com.example.authentication.entity.Account;
import com.example.authentication.repository.AccountRepository;
import com.example.authentication.repository.RoleRepository;
import com.example.authentication.request.authentication.LoginRequest;
import com.example.authentication.request.authentication.SignupRequest;
import com.example.authentication.response.authentication.AuthenticationResponse;
import com.example.authentication.security.JwtUtils;
import com.example.authentication.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public SignupRequest signupAccount(SignupRequest request) {
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setIsActive(false);
        if (CommonUtil.isNotEmpty(request.getRoles())) {
            var roles = roleRepository.listRoleByNames(request.getRoles());
            account.setRoles(roles);
        }
        var accountSave = accountRepository.save(account);
        return new SignupRequest(accountSave);
    }

    @Override
    public AuthenticationResponse login(LoginRequest form) {
        var account = accountRepository.findByEmail(form.getEmail());
        if (account.isEmpty()) {
            throw new ArgumentException("email", "username.notfound");
        }
        if (!passwordEncoder.matches(form.getPassword(), account.get().getPassword())) {
            throw new ArgumentException(
                    "password",
                    messageSource.getMessage("password.invalid", null, LocaleContextHolder.getLocale()));
        }
        var jwt = jwtUtils.generateToken(account.get().getEmail());
        return new AuthenticationResponse(account.get(), jwt);
    }

    @Override
    public Account getAccountByEmail(String email, String fieldName) {
        var account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new ArgumentException(fieldName, "username.notfound");
        }
        return account.get();
    }

}
