package com.edu.iuh.shop_managerment.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edu.iuh.shop_managerment.dto.request.AuthenticationRequest;
import com.edu.iuh.shop_managerment.dto.request.TokenRequest;
import com.edu.iuh.shop_managerment.dto.respone.AuthenticationRespone;
import com.edu.iuh.shop_managerment.dto.respone.TokenRespone;
import com.edu.iuh.shop_managerment.dto.respone.UserRespone;
import com.edu.iuh.shop_managerment.exception.AppException;
import com.edu.iuh.shop_managerment.exception.ErrorCode;
import com.edu.iuh.shop_managerment.models.InvalidatedToken;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.repositories.InvalidatedTokenRepository;
import com.edu.iuh.shop_managerment.repositories.UserRepository;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    UserRespone userRespone;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.expiration}")
    protected long EXPIRED_TIME;

    @NonFinal
    @Value("${jwt.refresh-expiration}")
    protected long REFRESH_EXPIRED_TIME;

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public AuthenticationRespone authenticate(AuthenticationRequest authenticationRequest) {
        Optional<User> foundUser = userRepository.findByUserName(authenticationRequest.getUserName());
        if (foundUser.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUNDED);
        }
        if (!checkPassword(authenticationRequest.getPassword(), foundUser.get().getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        String token = generateToken(foundUser.get());

        return AuthenticationRespone.builder()
                .userRespone(userRespone.getUserRespone(foundUser.get()))
                .token(token)
                .authentication(true)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("shopBag.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRED_TIME, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getUserRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Cannot create token: ", e);
            throw new RuntimeException(e);
        }
    }

    public TokenRespone introspect(TokenRequest tokenRequest) throws JOSEException, ParseException {
        String token = tokenRequest.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        log.info("Token is valid: {}", isValid);
        return TokenRespone.builder().validToken(isValid).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {

        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = isRefresh
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESH_EXPIRED_TIME, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);
        if (!(verified || expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public void logout(TokenRequest tokenRequest) throws ParseException, JOSEException {
        try {
            verifyToken(tokenRequest.getToken(), true);

            String jwtId =
                    SignedJWT.parse(tokenRequest.getToken()).getJWTClaimsSet().getJWTID();
            Date expiryTime =
                    SignedJWT.parse(tokenRequest.getToken()).getJWTClaimsSet().getExpirationTime();
            saveInvalidatedToken(jwtId, expiryTime);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    private void saveInvalidatedToken(String jwtId, Date expiryTime) {
        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jwtId).expriryDate(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    public TokenRespone refreshToken(TokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        saveInvalidatedToken(jwtId, expiryTime);

        String userId = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
        String newToken = generateToken(user);

        return TokenRespone.builder().token(newToken).validToken(true).build();
    }
}
