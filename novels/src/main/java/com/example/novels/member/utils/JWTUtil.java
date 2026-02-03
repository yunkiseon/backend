package com.example.novels.member.utils;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.management.RuntimeErrorException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

    // signature
    private static String key = "12316546987546446561070487978990231560651103";
    // 토큰 생성 메서드
    public static String generateToken(Map<String, Object> valueMap, int min){
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


        // jwtToken .의 앞부분 : Heager + payLoad => Base64 인코딩
        // . 뒷부분 암호환시킨 시그니처 값
        String jwtStr = Jwts.builder().subject("JWT")
        // 보낼 데이터
        .claims(valueMap)
        //발급시간
        .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
        //만료시간
        .expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
        .signWith(key)
        .compact();

        return jwtStr;
    }
    // 트콘 검증 메서드
    public static Map<String,Object> validateToken(String token){
        

        Map<String, Object> claim = null;
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("utf-8"));
            claim = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claim;
    }
    
}
