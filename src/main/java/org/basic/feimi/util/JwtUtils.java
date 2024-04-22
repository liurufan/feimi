package org.basic.feimi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import org.basic.feimi.security.SecurityConfiguration;

import java.util.Map;
import java.util.UUID;

public class JwtUtils {

    public static String buildJWTTokenForCookie(Object obj) throws JsonProcessingException {
        Map<String, Object> payload = JsonUtils.toMap(JsonUtils.toJson(obj));
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(payload)
                .setExpiration(null)
                .signWith(SecurityConfiguration.TOKEN_SECRET_KEY)
                .compact();
    }
}
