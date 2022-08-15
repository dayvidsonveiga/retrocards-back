package br.com.vemser.retrocards.security;

import br.com.vemser.retrocards.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;
    private static final String ROLES = "roles";
    private static final String TOKEN_PREFIX = "Bearer ";


    public String getToken(UserEntity userEntity) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.valueOf(expiration));

        List<String> listRoles = List.of(userEntity.getRole().getRoleName());

        String token = Jwts.builder()
                .setIssuer("retrocards")
                .claim(Claims.ID, userEntity.getIdUser())
                .claim(ROLES, listRoles)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TOKEN_PREFIX + token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
            return null;
        }

        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Integer idUsuario = body.get(Claims.ID, Integer.class);

        if (idUsuario != null) {
            List<String> roles = body.get(ROLES, List.class);

            List<SimpleGrantedAuthority> rolesGrantedAuthority = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .toList();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(idUsuario, null, rolesGrantedAuthority);

            return usernamePasswordAuthenticationToken;
        }
        return null;
    }

}
