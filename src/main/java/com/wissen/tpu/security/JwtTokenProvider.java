package com.wissen.tpu.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.wissen.tpu.dto.LoginResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private final String secretKey = "Qw4F5p7vGqfJqvJgqfT9p4FkjVZp2L1y7c9wqF==";
	private final long validityInMs = 1600_000; // 0.5 hour

	public String createToken(LoginResponse user) {

		Claims claims = Jwts.claims().setSubject(user.getCode());

		claims.put("role", user.getRole());
		claims.put("department", user.getDepartment());

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMs);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8)).compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
	}
}
