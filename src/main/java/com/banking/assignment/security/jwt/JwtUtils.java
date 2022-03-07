package com.banking.assignment.security.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.banking.assignment.exceptions.ConcurrentLoginException;
import com.banking.assignment.exceptions.CustomException;
import com.banking.assignment.payload.response.UserResponse;
import com.banking.assignment.persistence.entity.User;
import com.banking.assignment.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import static com.banking.assignment.constants.GlobalConstants.ISSUER;
import static com.banking.assignment.constants.GlobalConstants.ROLES;


@Component
@Slf4j
public class JwtUtils {

  @Autowired
  private InMemoryTokenHolder inMemoryTokenHolder;


  @Value("${banking.app.jwtSecret}")
  private String jwtSecret;

  @Value("${banking.app.jwtExpiration}")
  private int jwtExpiration;


  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    Instant issuedAt = Instant.now();
    String token = inMemoryTokenHolder.getTokenByUserName(userPrincipal.getUsername());
    if (token != null && validateToken(token)) {
      log.error("User is already login ",userPrincipal.getUsername());
      throw new ConcurrentLoginException(userPrincipal.getUsername());
    }
    List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
    String authToken =JWT.create().withIssuer(ISSUER).withJWTId(UUID.randomUUID().toString())
            .withIssuedAt(Date.from(issuedAt))
            .withExpiresAt(Date.from(issuedAt.plusSeconds(jwtExpiration))).withNotBefore(Date.from(issuedAt))
            .withSubject(userPrincipal.getUsername()).withClaim(ROLES, roles).sign(algorithm);
            inMemoryTokenHolder.putToken(userPrincipal.getUsername(), authToken);
    return authToken;

  }
  public UserResponse getUserFromToken(String authToken) {
    try {
      DecodedJWT jwt = JWT.decode(authToken);
      UserResponse userResponse = new UserResponse(jwt.getSubject(), jwt.getClaim(ROLES).asList(String.class));
      return userResponse;
    } catch (JWTDecodeException exception){
      throw new CustomException(exception.getMessage(), "getUserFromToken", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  public boolean validateToken(String authToken) {
    try {
      Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
      JWTVerifier verifier = JWT.require(algorithm)
              .withIssuer(ISSUER)
              .build();
      DecodedJWT jwt = verifier.verify(authToken);
      return true;
    } catch (TokenExpiredException exception){
      throw new CustomException(exception.getMessage(), "validateToken", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
