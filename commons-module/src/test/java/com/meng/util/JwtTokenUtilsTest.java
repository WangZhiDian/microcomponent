package com.meng.util;

import io.jsonwebtoken.Claims;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtilsTest extends TestCase {

    /*n(String audience, Map<String, Object> claimMap,
                                     String subject, String issuer,
                                     long ttl, String secretKey) {

                JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setAudience(audience)
                .setClaims(claimMap)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(createDate)
                .setExpiration(expiresDate)
                .signWith(key);*/
    @Test
    public void testCreateToken() {
        System.out.println("aa");
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("info", "{aa:bb}");
        String seckey = "asdfsdafsdfasdfasfsdfsdadsfkasjfoasflskjdfoisafjsldjfof";
        String token = JwtTokenUtils.createToken("audiency-a", claimMap, "subject_t", "issuer_t",
                20000L, seckey);
        System.out.println("token:" + token);

        //seckey = seckey + "lkaskfdjl";
        Claims cl = JwtTokenUtils.parseToken(token, seckey);
        System.out.println(cl.get("info"));
    }


}