package uz.pdp.quiz_first_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {


    public boolean validateToken(String authorization) {
        try {
            getClaims(authorization);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private static final String SECRET_KEY = "2QPSlwk7xsTL69sWY873j4uYQ5JIwLCbuS8e1qFXAB04imKP6t022RhS6EObxOkij02CJ78V6JGKio9szuvLlQ";

    private SecretKey getKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String getUserName(String token) {
        return getClaims(token).getSubject();
    }

    public List<GrantedAuthority> getRoles(String token) {
        String roles = getClaims(token).get("roles", String.class);
       return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Object generateRefreshToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getKey())
                .claim("roles", roles)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getKey())
                .claim("roles", roles)
                .compact();
    }
}
