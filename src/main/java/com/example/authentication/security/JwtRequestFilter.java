package com.example.authentication.security;

import com.example.authentication.Service.AccountService;
import com.example.authentication.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Value("${jwt.prefix}")
    private String jwtPrefix;
    private final AccountService userService;
    private final JwtUtils jwtUtils;
    @Autowired
    public JwtRequestFilter(AccountService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        String jwt = null;
        String email = null;
        if (authorization != null && authorization.startsWith(jwtPrefix)) {
            jwt = authorization.substring(jwtPrefix.length() + 1);
            email = jwtUtils.extractUsername(jwt);
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Account ue = userService.getAccountByEmail(email, "email");
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (var role : ue.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            UserDetails ud = new User(ue.getId().toString(), ue.getPassword(), authorities);
            if (jwtUtils.isTokenValid(jwt, email)) {
                UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
                        ud, ue, ud.getAuthorities());
                userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            }
        }
        chain.doFilter(request, response);
    }

}
