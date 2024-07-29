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
    @Value("${jwt.signerKeyRefresh}")
    protected String SIGNER_KEY_REFRESH;
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

    public AuthenticationRespone authenticate(AuthenticationRequest authenticationRequest) throws ParseException, JOSEException {
        Optional<User> foundUser = userRepository.findByEmail(authenticationRequest.getEmail());
        if (foundUser.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUNDED);
        }
        if (!checkPassword(authenticationRequest.getPassword(), foundUser.get().getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        String accessToken = generateToken(foundUser.get(), false);
        String refreshToken = generateToken(foundUser.get(), true);

        return AuthenticationRespone.builder()
                .userRespone(userRespone.getUserRespone(foundUser.get()))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authentication(true)
                .build();
    }

    private String generateToken(User user, boolean isRefresh) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("shopBag.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(isRefresh ? REFRESH_EXPIRED_TIME : EXPIRED_TIME, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getUserRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            if (isRefresh) {
                jwsObject.sign(new MACSigner(SIGNER_KEY_REFRESH.getBytes()));
            } else {
                jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            }
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Cannot create token: ", e);
            throw new RuntimeException(e);
        }
    }

    public TokenRespone introspect(TokenRequest tokenRequest) throws JOSEException, ParseException {
        boolean isValid = true;
        try {
            verifyToken(tokenRequest);
        } catch (AppException e) {
            isValid = false;
        }
        return TokenRespone.builder().validToken(isValid).build();
    }
    private SignedJWT verifyToken(TokenRequest tokenRequest) throws ParseException, JOSEException {
        boolean isRefresh = tokenRequest.isRefresh();
        String token = tokenRequest.getToken();

        JWSVerifier jwsVerifier = isRefresh ? new MACVerifier(SIGNER_KEY_REFRESH.getBytes())
                : new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus( isRefresh ? REFRESH_EXPIRED_TIME : EXPIRED_TIME, ChronoUnit.SECONDS)
                .toEpochMilli());

        var verified = signedJWT.verify(jwsVerifier);
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public void logout(TokenRequest tokenRequest) throws ParseException, JOSEException {
        try {
            verifyToken(tokenRequest);

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

    public TokenRespone refreshToken(TokenRequest request, boolean isLogin) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request);
        if (!isLogin) {
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            saveInvalidatedToken(jwtId, expiryTime);
        }

        String userId = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
        String accessToken = generateToken(user, false);
        String refreshToken = generateToken(user, true);

        return TokenRespone.builder().accessToken(accessToken).refreshToken(refreshToken).validToken(true).build();
    }
}
