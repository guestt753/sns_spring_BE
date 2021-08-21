package com.my.sns.config;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.my.sns.security.entity.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	@Autowired
	CustomUserDetails customUserDetails;
	Long userNo;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
	private static final String USER_NO = "userNo";
	
	private final Key key;
	Date tokenExpiresIn;
	
	public JwtProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey); 
		this.key = Keys.hmacShaKeyFor(keyBytes); 
	} 

	
	// generate access token
	public String generateJwtToken(Authentication authentication) {
		String name = authentication.getName();
		tokenExpiresIn = new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		if(authentication.getPrincipal() instanceof CustomUserDetails) {
			 userNo = ((CustomUserDetails)authentication.getPrincipal()).getUserNo();
			 System.out.println("userNo : " + userNo);
		}
		return Jwts.builder()
				.setSubject(name)
				.claim(USER_NO, userNo)
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		
	}
	
	// generate refresh token
	public String generateJwtToken() {
		tokenExpiresIn = new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME);
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	} 
	
	public String getUserNameFromJwtToken(String token) {
		return parseClaims(token).getSubject();
	}
	
	public Long getUserNoFromJwtToken(String token) {
		return Long.parseLong(parseClaims(token).get(USER_NO).toString());
	}
	
	public Long getExpireTime(String token) {
		return parseClaims(token).getExpiration().getTime();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException  e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		
		return false;
	} 
	
	// 만료된 토큰도 정보를 꺼내기 위해서 따로 분리함..
	private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}


