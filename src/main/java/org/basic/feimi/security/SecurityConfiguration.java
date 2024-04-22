package org.basic.feimi.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    public static final long TOKEN_LIFETIME = 3_600_000 * 24 * 7; // 1 week
    public static final int TOKEN_LIFETIME_IN_SECOND = 3_600 * 24 * 7; // 1 week
    public static final int TOKEN_REFRESH_IN_MINUTE = 5;

    public static final String TOKEN_PREFIX = "Bearer";
    public static final String KEY_STRING = "bzRbDEvedldqEEr1STBijjJn8Tak6gwR0G7ou1Lk1rMIqmLjwTyVqBlaXWOew60BtPtH3SIBXIPJw15ysrBGixU2fUP0PosS9uGn";
    public static final SecretKey TOKEN_SECRET_KEY = new SecretKeySpec(KEY_STRING.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() throws NoSuchAlgorithmException {
        return new LoginUrlAuthenticationEntryPoint("/login");
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() throws NoSuchAlgorithmException {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() throws NoSuchAlgorithmException {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() throws NoSuchAlgorithmException {
        return new CustomLogoutSuccessHandler();
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/*.html",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/login/**",
            "/logout",
            "/user/get-logged-in-user",
            "/user/register",
            "/user/check-email-exist",
            "/user/verify-account",
            "/mst/**",
            "/third-party-api/**",
            "/user/reset-password",
            "/user/save-password"
    };

    @NonNull
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(myUserDetailsService);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            if (e instanceof InternalAuthenticationServiceException) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            String json = String.format("{\"message\": \"%s\"}", e.getMessage());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        });
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.httpBasic().disable()
                .formLogin().disable()
                //.formLogin().loginProcessingUrl("/login").permitAll().usernameParameter("username").passwordParameter("password")
                //.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler)
                //.and()
                //.logout().disable()
                .logout()
                .logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .addFilter(new TokenBasedAuthenticationFilter(authenticationManager(), authenticationSuccessHandler, authenticationFailureHandler))
                .addFilter(new TokenBasedAuthorizationFilter(authenticationManager()));
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/**").authenticated();
    }
}
