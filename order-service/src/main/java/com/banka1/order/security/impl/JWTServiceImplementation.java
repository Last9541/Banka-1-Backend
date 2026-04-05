package com.banka1.order.security.impl;

import com.banka1.order.security.JWTService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
public class JWTServiceImplementation implements JWTService {

    private final JWSSigner signer;

    @Value("${banka.security.roles-claim:roles}")
    private String roleClaim;

    @Value("${banka.security.permissions-claim:permissions}")
    private String permissionClaim;

    @Value("${banka.security.issuer:banka1}")
    private String issuer;

    @Value("${banka.security.expiration-time:3600000}")
    private Long expirationTime;

    public JWTServiceImplementation(@Value("${jwt.secret}") String secret) throws KeyLengthException {
        this.signer = new MACSigner(secret);
    }

    @Override
    public String generateJwtToken() {
        List<String> permissions = new ArrayList<>();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("order-service")
                .issuer(issuer)
                .claim(roleClaim, "SERVICE")
                .claim(permissionClaim, permissions)
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                .build();

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        SignedJWT jwt = new SignedJWT(header, claims);
        try {
            jwt.sign(signer);
        } catch (Exception e) {
            throw new IllegalStateException("Greška pri potpisivanju sistemskog JWT-a", e);
        }
        return jwt.serialize();
    }
}
