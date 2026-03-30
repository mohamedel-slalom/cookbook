package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

	@Value("${jwt.secret:mySecretKeyForJWTThatIsAtLeast32CharactersLong}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}")
	private long jwtExpiration;

	public String generateToken(String username, String role) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

		return Jwts.builder()
			.setSubject(username)
			.claim("role", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims validateToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

		return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	public String getUsernameFromToken(String token) {
		return validateToken(token).getSubject();
	}

	public String getRoleFromToken(String token) {
		return (String) validateToken(token).get("role");
	}

	public boolean isTokenValid(String token) {
		try {
			validateToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
