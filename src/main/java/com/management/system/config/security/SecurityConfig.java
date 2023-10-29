package com.management.system.config.security;

import com.management.system.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    public static final String[] ENDPOINTS_WHITELIST = {
    		"/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico",

           "/resources/static/js/**",
          "/resources/static/css/**",
            
            "/WEB-INF/jsp/login.html",
            "/WEB-INF/jsp/register.html",
            "/WEB-INF/jsp/home.html",
            "/WEB-INF/jsp/templates/message.html",
            "/WEB-INF/jsp/templates/menu2.html",
            
//            "/", "/index.html", "/favicon.ico", "/**/*.js",
//            "/**/*.js.map", "/**/*.css", "/assets/images/*.png",
//            "/assets/images/*.jpg", "/assets/images/*.jpeg", 
//            "/assets/images/*.gif", "/**/*.ttf", "/**/*.json",
//            "/**/*.woff", "/**/*.woff2", "/**/*.eot", "/**/*.svg",
           
            "/api/content/management/service/views/registerForm",
            "/api/content/management/service/views/home",
            "/api/content/management/service/members/register",
            "/api/content/management/service/contents/getImage/**"
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                
                
                .rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices())
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .failureUrl(LOGIN_URL + "?error=true")
                                .successHandler(successHandler())
                                .usernameParameter("email")
                                .permitAll())

                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                                .logoutSuccessUrl(LOGIN_URL + "?logout=true")
                                .permitAll())

                .authenticationProvider(authProvider())
                .build();
    }


    @Bean
    WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers( 
        		"/css/**", "/js/**", "/images/**", "/favicon.ico");
    }

	/*
	 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return (web) ->
	 * web.debug(false) .ignoring() .antMatchers("/css/**", "/js/**", "/img/**",
	 * "/lib/**", "/favicon.ico"); }
	 */
    
    private AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
        simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl("/api/content/management/service/views/viewContent");
        return simpleUrlAuthenticationSuccessHandler;
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("myKey", userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }
}
