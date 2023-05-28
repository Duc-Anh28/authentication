package com.example.authentication.configuration;

import com.example.authentication.entity.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class
AuditorAwareImpl implements AuditorAware<Account> {

    public Optional<Account> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(obj -> !(obj instanceof AnonymousAuthenticationToken))
                .map(Authentication::getCredentials)
                .map(Account.class::cast);
    }

}
