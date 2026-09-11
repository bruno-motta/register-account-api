package com.bruno_motta.account.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    private static final long TOKEN_EXPIRATION_HOURS = 2;

    private final Algorithm algorithm;
    private final String issuer;
    private final JWTVerifier verifier;

    public TokenService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String generateToken(UserDetailsImpl userDetails) {
        Instant expiration = Instant.now().plus(TOKEN_EXPIRATION_HOURS, ChronoUnit.HOURS);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(expiration))
                .sign(algorithm);
    }

    public String extractSubject(String token) {
        return verify(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private DecodedJWT verify(String token) {
        return verifier.verify(token);
    }
}
