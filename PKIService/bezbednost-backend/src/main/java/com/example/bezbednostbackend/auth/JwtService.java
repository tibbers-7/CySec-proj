package com.example.bezbednostbackend.auth;

import com.zaxxer.hikari.pool.HikariProxyCallableStatement;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //https://allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
    private static final String SECRET_KEY="67556B58703272357538782F413F4428472B4B6250655368566D597133743676";



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails){
        return generateAccessToken(new HashMap<>(),userDetails);
    }

    public String generateAccessToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // token valid for 15 minutes
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 15))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean JwtSignatureIsValid(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            // If the code reaches here, the signature is valid
            return true;
        } catch (Exception e) {
            // Signature verification failed
            return false;
        }
    }

    public String calculateHMACOfToken(String token) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // Create an instance of the Mac algorithm with HMAC-SHA256
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);

        // Calculate the HMAC of the token
        byte[] hmacBytes = mac.doFinal(token.getBytes(StandardCharsets.UTF_8));

        // Encode the HMAC bytes as a Base64 string
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hmacBytes);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
        // Handle exceptions
        e.printStackTrace();
        return null;
        }
    }
}
