package org.basic.feimi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.basic.feimi.util.CookieUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.basic.feimi.security.SecurityConfiguration.*;
public class TokenBasedAuthorizationFilter extends BasicAuthenticationFilter {

    TokenBasedAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorizationToken = null;
        Cookie cookie = CookieUtils.getCookie(request, HttpHeaders.AUTHORIZATION);
        if (cookie != null) {
            authorizationToken = cookie.getValue();
        }

        if (authorizationToken != null && authorizationToken.startsWith(TOKEN_PREFIX)) {
            authorizationToken = authorizationToken.replaceFirst(TOKEN_PREFIX, "");
            try {
                Claims claims = Jwts.parserBuilder().setSigningKey(TOKEN_SECRET_KEY).build()
                        .parseClaimsJws(authorizationToken)
                        .getBody();

                /*
                 * 10分ごと新しいtokenを発行する
                 * */
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, TOKEN_LIFETIME_IN_SECOND / 60 - TOKEN_REFRESH_IN_MINUTE);
                if (claims.getExpiration().compareTo(calendar.getTime()) < 0) {
                    String newToken = newTokenWithSameStats(claims);
                    cookie.setValue(TOKEN_PREFIX + newToken);
                    cookie.setMaxAge(TOKEN_LIFETIME_IN_SECOND);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }

                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(claims.get("generatedId"), null, Collections.emptyList()));
                chain.doFilter(request, response);
            } catch (JwtException e) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private static String newTokenWithSameStats(Map<String, Object> jwtPayLoad) {
        jwtPayLoad.put("iat", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis()));
        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(jwtPayLoad)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                .signWith(TOKEN_SECRET_KEY)
                .compact();
        return token;
    }
}
