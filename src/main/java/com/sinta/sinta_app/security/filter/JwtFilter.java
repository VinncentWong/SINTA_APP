package com.sinta.sinta_app.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sinta.sinta_app.security.authentication.JwtAuthentication;
import com.sinta.sinta_app.security.manager.JwtManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter{
    
    private final JwtManager manager;

    @Autowired
    public JwtFilter(JwtManager manager){
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        } else {
            if(!token.startsWith("Bearer")){
                response.sendError(HttpStatus.FORBIDDEN.value(), "header authorization tidak valid");
                return;
            }
            token = token.substring(7);
            log.info("token = " + token);
            Authentication auth = this.manager.authenticate(new JwtAuthentication(token, null));
            if(!auth.isAuthenticated()){
                response.sendError(HttpStatus.FORBIDDEN.value(), "token jwt tidak valid");
                return;
            } else {
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            }
        }
    }
}
