package org.basic.feimi.domain.service;

import io.jsonwebtoken.Jwts;
import org.basic.feimi.infrastructure.entity.User;
import org.basic.feimi.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class AuthService {
    @Autowired
    UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    public String buildJWTToken(String userId) {
        User user = userService.findUserByEmail(userId);
        Map<String, Object> jwtPayLoad = buildTokenPayLoadForUser(user.getGeneratedId());
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(jwtPayLoad)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConfiguration.TOKEN_LIFETIME))
                .signWith(SecurityConfiguration.TOKEN_SECRET_KEY)
                .compact();
    }

    public Map<String, Object> buildTokenPayLoadForUser(String generatedId) {
        Map<String, Object> jwtPayLoad = new HashMap<>();
        jwtPayLoad.put("generatedId", generatedId);
        jwtPayLoad.put("iat", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis()));
        return jwtPayLoad;
    }

}
