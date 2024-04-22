package org.basic.feimi.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 認証ユーザー情報ユーティリティクラス
 */
public class UserUtils {

    /**
     * 認証ユーザー 自動生成ID を取得する。
     *
     * @return String 認証ユーザー 自動生成ID
     */
    public static String getLoggedInGeneratedId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }
        return (String) authentication.getPrincipal();
    }

    /**
     * ログイン状態を取得する。
     *
     * @return ログイン状態
     * @retval true ログイン中
     * @retval false 未ログイン
     */
    public static boolean isLoggedIn() {
        return getLoggedInGeneratedId() != null;
    }
}
