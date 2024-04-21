package com.example.authentication.security;

import com.example.authentication.Service.AccountService;
import com.example.authentication.entity.Account;
import com.example.authentication.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final AccountService accountService;
    private final MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account accounts = accountService.getAccountSecurityByEmail(email, "email");
        if (CommonUtil.isNotEmpty(accounts)) {
            return new AccountUserDetails(accounts);
        } else {
            throw new UsernameNotFoundException(messageSource.getMessage("username.notfound", null, LocaleContextHolder.getLocale()));
        }
    }

}
