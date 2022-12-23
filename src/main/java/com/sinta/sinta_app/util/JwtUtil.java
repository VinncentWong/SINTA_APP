package com.sinta.sinta_app.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sinta.sinta_app.entity.Extractable;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {
    
    @Value("${JWT_KEY}")
    private String secretKey;

    public String generateToken(Extractable data){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", data.getId());
        claims.put("username", data.getUsername());
        claims.put("role", data.getRole());
        return Jwts.builder()
                    .signWith(SignatureAlgorithm.HS256, this.secretKey.getBytes())
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + 100000000L))
                    .compact();
    }

    public boolean validate(String token){
        try{
            Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(token);
            return true;
        }
        catch(Exception ex){
            return false;
        }
        
    }
}
