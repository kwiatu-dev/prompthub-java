package com.springboot.prompthub.security;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.utils.AppConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtTokenProvider {

    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        Claims claims = Jwts.claims()
                .subject(authentication.getName())
                .issuedAt(currentDate)
                .expiration(expireDate)
                .notBefore(currentDate)
                .add("authorities", authentication.getAuthorities())
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(key())
                .compact();
    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getEmail(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);

            return true;
        }
        catch (MalformedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, AppConstant.ERROR_JWT_INVALID_TOKEN);
        }
        catch (ExpiredJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, AppConstant.ERROR_JWT_EXPIRED_TOKEN);
        }
        catch (SignatureException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, AppConstant.ERROR_JWT_UNSUPPORTED_TOKEN);
        }
        catch (IllegalArgumentException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, AppConstant.ERROR_JWT_CLAIMS_EMPTY);
        }
    }
}
