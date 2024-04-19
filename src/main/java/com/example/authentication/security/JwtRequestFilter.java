package com.example.authentication.security;

import com.example.authentication.Service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Value("${jwt.prefix}")
    private String jwtPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        log.info("getRequestURI: " + request.getRequestURI());
        String jwt = null;
        String email = null;
        if (authorization != null && authorization.startsWith(jwtPrefix)) {
            jwt = authorization.substring(jwtPrefix.length() + 1);
            email = jwtUtils.extractUsername(jwt);
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = userDetailsService.loadUserByUsername(email);
            if (jwtUtils.isTokenValid(jwt, email)) {
                var userAuthentication = new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
                userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            }
        }
        chain.doFilter(request, response);
    }

}
