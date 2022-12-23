package com.sinta.sinta_app.security.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.sinta.sinta_app.security.authentication.JwtAuthentication;
import com.sinta.sinta_app.util.JwtUtil;

@Component
public class JwtProvider implements AuthenticationProvider{

    private final JwtUtil util;

    @Autowired
    public JwtProvider(JwtUtil util){
        this.util = util;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getPrincipal().toString();
        boolean tokenValid = this.util.validate(token);
        if(!tokenValid){
            return new JwtAuthentication(null, null);
        } else {
            List<GrantedAuthority> auths = new ArrayList<>();
            auths.add(new SimpleGrantedAuthority("VALIDATED"));
            return new JwtAuthentication(null, null, auths);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if(authentication.equals(JwtAuthentication.class)){
            return true;
        } else {
            return false;
        }
    }
    
}
