package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.dto.request.AuthenticationRequest;
import com.edu.iuh.shop_managerment.dto.respone.ApiReponse;
import com.edu.iuh.shop_managerment.dto.respone.AuthenticationRespone;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.exception.AppException;
import com.edu.iuh.shop_managerment.exception.ErrorCode;
import com.edu.iuh.shop_managerment.models.User;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRespone userRespone;
    @NonFinal
//    @Value("${jwt.signerKey}")
    protected final static String SIGNER_KEY = "AU9hoTlqMf8kZiQFMad+qGhyEvW2ixGuMSx8BR9lE++kORpbf/FlPbSDhfSXTsFW";
    public boolean checkPassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }
    public AuthenticationRespone authenticate(AuthenticationRequest authenticationRequest){
        User foundUser = userService.findByEmail(authenticationRequest.getEmail());
        if(!checkPassword(authenticationRequest.getPassword(),foundUser.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        String token = generateToken(authenticationRequest.getEmail(),foundUser.getId());

        return AuthenticationRespone.builder()
                .userRespone(userRespone.getUserRespone(foundUser))
                .token(token)
                .authentication(true)
                .build();

    }
    private String generateToken(String email, String userId) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("shopBag.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("userId", userId)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e){
            log.error("Cannot create token: ",e);
            throw new RuntimeException(e);
        }
    }
    public boolean introspect(String token) throws JOSEException, ParseException {
        System.out.printf("token: ",token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        return verified && expiryTime.after(new Date());
    }
}
