package org.basic.feimi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import org.basic.feimi.security.SecurityConfiguration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CookieUtils {
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void addStringToCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
        Cookie tokenCookie = getCookie(request, name);
        if(tokenCookie == null) {
            tokenCookie = new Cookie(name, value);
            tokenCookie.setHttpOnly(true);
        } else {
            tokenCookie.setValue(value);
        }
        tokenCookie.setMaxAge(-1);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
    }

    public static String getStringFromCookie(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if(cookie == null){
            return null;
        }else{
            return cookie.getValue();
        }
    }

    /**
     * cookie を response に追加する
     * @param name
     * @param obj
     * @param request
     * @param response
     * @throws JsonProcessingException
     */
    public static void addObjectToCookie(String name, Object obj, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String token = JwtUtils.buildJWTTokenForCookie(obj);
        Cookie tokenCookie = getCookie(request, name);
        if(tokenCookie == null) {
            tokenCookie = new Cookie(name, token);
            tokenCookie.setHttpOnly(true);
        } else {
            tokenCookie.setValue(token);
        }
        tokenCookie.setMaxAge(-1);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
    }

    /**
     * cookie を request から取って、希望オブジェクトクラスに変更
     * @param request
     * @param name
     * @param objectClass
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromCookie(HttpServletRequest request, String name, Class<T> objectClass) {
        Cookie cookie = getCookie(request, name);
        try {
            Map<String, Object> map = Jwts.parserBuilder().setSigningKey(SecurityConfiguration.TOKEN_SECRET_KEY).build()
                    .parseClaimsJws(cookie.getValue())
                    .getBody();
            return JsonUtils.mapper.convertValue(map, objectClass);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Cookie を削除する
     * @param request
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookie(request, name);
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}