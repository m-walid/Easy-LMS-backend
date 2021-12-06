package com.lilwel.elearning.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lilwel.elearning.Security.AuthPrincipal;
import com.lilwel.elearning.Security.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
@Slf4j
public class JwtUtil {
    private static Algorithm algo = Algorithm.HMAC256("secretjwtKey".getBytes());
    public static  String generateToken(AuthUser user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId",user.getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis()+200 * 60 * 1000))
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algo);

    }

    public static UsernamePasswordAuthenticationToken verifyToken(String token){
        JWTVerifier verifier = JWT.require(algo).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        String userIdString = decodedJWT.getClaim("userId").toString().replaceAll("\"","");
        UUID userId = UUID.fromString(userIdString);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role->{
            authorities.add(new SimpleGrantedAuthority((role)));
        });
        AuthPrincipal authPrincipal = new AuthPrincipal(username,userId);
        return  new UsernamePasswordAuthenticationToken(authPrincipal,null,authorities);
    }

}
