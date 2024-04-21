package com.example.authentication.helper;

import com.example.authentication.controller.exception.ApplicationException;
import com.example.authentication.entity.Account;
import com.example.authentication.security.AccountUserDetails;
import com.example.authentication.util.CommonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationHelper {

    public Account getUser() {
        Optional<Account> accountOptional = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof AccountUserDetails) return (((AccountUserDetails) u).getAccount());
                    return null;
                });
        if (accountOptional.isEmpty()) throw new ApplicationException("access-denied");
        return accountOptional.get();
    }

    public List<String> getUserRoleList(Account account) {
        List<String> userRoles = new ArrayList<>();
        if (CommonUtil.isEmpty(account)) throw new ApplicationException("access-denied");
        if (account.getRoles() != null) {
            var accountRoles = account.getRoles();
            for (var accountRole : accountRoles) {
                userRoles.add(accountRole.getName());
            }
        }
        return userRoles;
    }

}
