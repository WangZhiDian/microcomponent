package com.meng.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * jwt token utils
 *
 * @author : sunyuecheng
 */
public final class JwtTokenUtils {
    /**
     * create token
     *
     * @param audience  : 监听者
     * @param claimMap  : 加密的数据，map形式存储
     * @param subject   : 主题
     * @param issuer    :
     * @param ttl       : 过期时间
     * @param secretKey : 加密key，长度有要求，需要比较长
     * @return String :
     */
    public static String createToken(String audience, Map<String, Object> claimMap,
                                     String subject, String issuer,
                                     long ttl, String secretKey) {
        if (StringUtils.isEmpty(audience) || StringUtils.isEmpty(secretKey)) {
            return null;
        }

        Date createDate = new Date();
        Date expiresDate = createDate;

        if (ttl >= 0) {
            expiresDate = new Date(createDate.getTime() + ttl);
        }
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setAudience(audience)
                .setClaims(claimMap)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(createDate)
                .setExpiration(expiresDate)
                .signWith(key);
        return builder.compact();
    }

    /**
     * parse token
     *
     * @param jwtToken  :
     * @param secretKey :
     * @return Claims :
     */
    public static Claims parseToken(String jwtToken, String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwtToken).getBody();
    }


    /**
     * create token
     *
     * @param audience   :
     * @param claimMap   :
     * @param subject    :
     * @param issuer     :
     * @param ttl        :
     * @param privateKey :
     * @return String :
     */
    public static String createToken(String audience, Map<String, Object> claimMap,
                                     String subject, String issuer,
                                     long ttl, Key privateKey) {
        if (StringUtils.isEmpty(audience) || privateKey == null) {
            return null;
        }

        Date createDate = new Date();
        Date expiresDate = createDate;

        if (ttl >= 0) {
            expiresDate = new Date(createDate.getTime() + ttl);
        }

        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setAudience(audience)
                .setClaims(claimMap)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(createDate)
                .setExpiration(expiresDate)
                .signWith(privateKey);
        return builder.compact();
    }

    /**
     * parse token
     *
     * @param jwtToken  :
     * @param publicKey :
     * @return Claims :
     */
    public static Claims parseToken(String jwtToken, Key publicKey) {

        return Jwts.parserBuilder().setSigningKey(publicKey).build()
                .parseClaimsJws(jwtToken).getBody();
    }

    private JwtTokenUtils() {
    }
}