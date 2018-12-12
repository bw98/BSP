package com.BSP.Util;

import com.BSP.bean.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;


public class JWTUtil {

    public static SecretKey generalKey(){
        String stringKey = "SDFEEdfdeFDRE";
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static String generateSub(User sub) {
        sub.setPassword(null);
        JSONObject jsonObject = JSONObject.fromObject(sub);
        String jsonStr = jsonObject.toString();
        return jsonStr;
    }

    public static String createJWT(User user, long ttlMills, Map<String, Object> claims) throws Exception{
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey key = generalKey();
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        String sub = generateSub(user);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(user.getUserName())
                .setIssuedAt(now)
                .setSubject(sub)
                .signWith(signatureAlgorithm, key);

        if (ttlMills >= 0) {
            long expMills = nowMills + ttlMills;
            Date expDate = new Date(expMills);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwtStr) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr).getBody();

        return claims;
    }

}