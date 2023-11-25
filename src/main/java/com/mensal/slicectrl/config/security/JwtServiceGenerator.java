package com.mensal.slicectrl.config.security;

import com.mensal.slicectrl.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceGenerator {

    public String generateToken(Usuario userDetails) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", userDetails.getId().toString());
        extraClaims.put("cpf", userDetails.getCpf());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getCpf())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + 3600000 * JwtParameters.HORAS_EXPIRACAO_TOKEN))
                .signWith(getSigningKey(), JwtParameters.ALGORITMO_ASSINATURA)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        return !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtParameters.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

}

