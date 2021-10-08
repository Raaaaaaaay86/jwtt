package com.example.jwtt.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
	private String SECRET_KEY = "HSFKLJGHFSDGJKLFSGHDSFKLGHDSFGKJDSFKJLHDSFKJGHDSFKJLHDJSKNXJKULFDH";
	private final String CLAIMS_KEY_USER_ROLES = "userRoles";

	public String createToken(String userName, List<String> userRoles) {
		int oneDaySeconds = 86400;

		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(Instant.now().plusSeconds(oneDaySeconds)))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
				.addClaims(Map.of(CLAIMS_KEY_USER_ROLES, userRoles))
				.compact();

	}

	public Claims parseToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String parseUserNameFromToken(String token) {
		return parseToken(token).getSubject();
	}

	public List<SimpleGrantedAuthority> parseUserAuthoritiesFromToken (String token) {
		List<String> userRoles = parseToken(token).get(CLAIMS_KEY_USER_ROLES, List.class);
		return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole)).collect(Collectors.toList());
	}
}
