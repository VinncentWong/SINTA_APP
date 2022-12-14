package com.sinta.sinta_app.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.sinta.sinta_app.security.provider.JwtProvider;

@Component
public class JwtManager implements AuthenticationManager{
    
    private final JwtProvider jwtProvider;

    @Autowired
    public JwtManager(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return this.jwtProvider.authenticate(authentication);
    }

    
}
