package com.my.sns.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.my.sns.user.impl.UserDAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.io.Decoders;

@Component
public class MySigningKeyResolver extends SigningKeyResolverAdapter {
	@Autowired
	UserDAO userDAO;
	private static final String USER_NO = "userNo";

	@Override
	public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
		String keyId = header.getKeyId(); //or any other field that you need to inspect
        Long userNo = Long.parseLong(claims.get(USER_NO).toString());
        System.out.println("resolver 호출 (userNo 값) : " + userNo);
        
        String key = lookupVerificationKey(keyId, userNo); //implement me
        byte[] decodedKey = Decoders.BASE64.decode(key);
        System.out.println("decodedKey : " + decodedKey);
		return decodedKey;
	}

//	@Override
//	public Key resolveSigningKey(JwsHeader header, Claims claims) {
//		String keyId = header.getKeyId(); //or any other field that you need to inspect
//        Long userNo = Long.parseLong(claims.get(USER_NO).toString());
//        System.out.println("resolver 호출 (userNo 값) : " + userNo);
//        
//        String key = lookupVerificationKey(keyId, userNo); //implement me
//        byte[] decodedKey = Base64.getDecoder().decode(key);
//        Key originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HS256");
//        System.out.println("orginalKey : " + originalKey);
//		return originalKey;
//	}

	@Transactional
	public String lookupVerificationKey(String keyId, Long userNo) {
		String accessTokenSecretKey = null;
		String refreshTokenSecretKey = null;
		accessTokenSecretKey = userDAO.getUserByUserNo(userNo).getAccessTokenSecretKey();
		refreshTokenSecretKey = userDAO.getRefreshTokenByuserNo(userNo).getRefreshTokenSecretKey();
		
		if(accessTokenSecretKey == null || refreshTokenSecretKey == null) {
			throw new RuntimeException("DB에서 Key를 불러 올 수 없습니다.");
		}
		System.out.println("accessTokenSecretKey : " + accessTokenSecretKey);
		System.out.println("refreshTokenSecretKey : " + refreshTokenSecretKey);
		
		String key = keyId.substring(10, keyId.length());
		
		System.out.println("추출한 키 : " + key);
		
		if(!accessTokenSecretKey.equals(key) || !refreshTokenSecretKey.equals(key)) {
			throw new RuntimeException("Key값이 일치하지 않습니다.");
		}
	
		return accessTokenSecretKey;
	}
}
