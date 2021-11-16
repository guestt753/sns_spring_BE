package com.my.sns.config;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.my.sns.security.entity.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	@Autowired
	CustomUserDetails customUserDetails;
	@Autowired
	MySigningKeyResolver signingKeyResolver;
	Long userNo;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
//	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 4;            // 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
	private static final String USER_NO = "userNo";
	
//	@Value("${jwt.secret}")
//	private static String jwtSecret;
	private SecretKey key;
	private String secretString;
	private byte[] decodeBytes;
	private Date tokenExpiresIn;
	
//	public JwtProvider() {
////		byte[] keyBytes = Decoders.BASE64.decode(secretKey); 
////		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		secretString = Encoders.BASE64.encode(key.getEncoded());
//		decodeBytes = Decoders.BASE64.decode(secretString);
////		signingKeyResolver = new MySigningKeyResolver();
////		System.out.println("scretkey : " + this.key);
////		System.out.println("scretkey(base64 encoded) : " + secretString);
////		System.out.println("key(decoded) : " + decodeBytes);
//	} 

	
	// generate access token
	public String generateAccessJwtToken(Authentication authentication) {
		System.out.println("::::::::generateAccessJwtToken:::::::::");
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		secretString = Encoders.BASE64.encode(key.getEncoded());
		decodeBytes = Decoders.BASE64.decode(secretString);
		String name = authentication.getName();
		String keyId = getAccessKeyId(secretString);
		tokenExpiresIn = new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		if(authentication.getPrincipal() instanceof CustomUserDetails) {
			 userNo = ((CustomUserDetails)authentication.getPrincipal()).getUserNo();
//			 System.out.println("userNo : " + userNo);
		}
//		System.out.println("생성된 토큰의 key : " + key);
//		System.out.println("생성된 토큰의 key(encoded) : " + secretString);
//		System.out.println("생성된 토큰의 keyId : " + keyId);
//		System.out.println("key(decoded) : " + decodeBytes);
		return Jwts.builder()
				.setHeaderParam(JwsHeader.KEY_ID, keyId)
				.setSubject(name)
				.claim(USER_NO, userNo)
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiresIn)
				.signWith(key)
				.compact();
		
	}
	
	// generate refresh token
	public String generateRefreshJwtToken(Authentication authentication) {
		System.out.println("::::::::generateRefreshJwtToken:::::::::");
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		secretString = Encoders.BASE64.encode(key.getEncoded());
		decodeBytes = Decoders.BASE64.decode(secretString);
		String keyId = getRefreshKeyId(secretString);
		tokenExpiresIn = new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME);
		if(authentication.getPrincipal() instanceof CustomUserDetails) {
			 userNo = ((CustomUserDetails)authentication.getPrincipal()).getUserNo();
//			 System.out.println("userNo : " + userNo);
		}
//		System.out.println("생성된 재발급토큰의 key : " + key);
//		System.out.println("생성된 재발급토큰의 key(encoded) : " + secretString);
//		System.out.println("생성된 토큰의 keyId : " + keyId);
		return Jwts.builder()
				.setHeaderParam(JwsHeader.KEY_ID, keyId)
				.claim(USER_NO, userNo)
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiresIn)
				.signWith(key)
				.compact();
	} 
	
	
	
	public String getKeytoString() {
		return secretString;
	}
	
	public String getAccessKeyId(String key) {
		String randomString = RandomStringUtils.randomAlphanumeric(10);
		return randomString + key;
	}
	
	public String getRefreshKeyId(String key) {
		String randomString = RandomStringUtils.randomAlphanumeric(10);
		return randomString + key;
	}

//	public String getUserNameFromJwtToken(String token, boolean type) {
//		return parseClaims(token, type).getSubject();
//	}
//	
//	public Long getUserNoFromJwtToken(String token, boolean type) {
//		return Long.parseLong(parseClaims(token, type).get(USER_NO).toString());
//	}
//	
//	public Long getExpireTime(String token, boolean type) {
//		return parseClaims(token, type).getExpiration().getTime();
//	}
	
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
			System.out.println("::::::::validateJwtToken:::::::::");
//			System.out.println("key : " + key);
//			System.out.println("key(encoded) : " + secretString);
//			System.out.println("key(decoded) : " + decodeBytes);
			Jwts.parserBuilder().setSigningKeyResolver(signingKeyResolver).build().parseClaimsJws(authToken);
	
//			Jwts.parserBuilder().setSigningKey(decodeBytes).build().parseClaimsJws(authToken);
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
	
	// 만료된 토큰도 정보를 꺼내기 위해서 따로 분리함.. true는 signingKeyResolver 호출
//	private Claims parseClaims(String accessToken, boolean type) {
//		if (type) {
//	        try {
//	            return Jwts.parserBuilder().setSigningKeyResolver(signingKeyResolver).build().parseClaimsJws(accessToken).getBody();
//	        } catch (ExpiredJwtException e) {
//	            return e.getClaims();
//	        }
//	    } else {
//	    	 try {
//		            return Jwts.parserBuilder().setSigningKey(getKeytoString()).build().parseClaimsJws(accessToken).getBody();
//		        } catch (ExpiredJwtException e) {
//		            return e.getClaims();
//		        }
//	    }
//	}
	private Claims parseClaims(String accessToken) {
		System.out.println("::::::::parseClaims:::::::::");
	        try {
	            return Jwts.parserBuilder().setSigningKeyResolver(signingKeyResolver).build().parseClaimsJws(accessToken).getBody();
	        } catch (ExpiredJwtException e) {
	            return e.getClaims();
	        } 
	}
}

