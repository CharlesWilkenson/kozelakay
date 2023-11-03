package com.management.system.config.security;

import com.management.system.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";

    private static final String ADMIN = "ADMIN";
    private static final String MEMBER = "MEMBER";

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       return http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(antMatcher("/api/content/management/service/views/view-usersForm")).hasAuthority(ADMIN)
                       .requestMatchers(antMatcher("/api/content/management/service/members/change-status/**")).hasAuthority(ADMIN)
                       .requestMatchers(antMatcher("/api/content/management/service/members/view-users")).hasAuthority(ADMIN)

                       .requestMatchers(antMatcher("/api/content/management/service/members/viewContent")).hasAnyAuthority(ADMIN, MEMBER)

                       .requestMatchers(antMatcher("/api/content/management/service/views/editProfile")).hasAnyAuthority(ADMIN, MEMBER)
                       .requestMatchers(antMatcher("/api/content/management/service/views/registerForm")).permitAll()
                       .requestMatchers(antMatcher("/api/content/management/service/views/addContent")).hasAnyAuthority(ADMIN, MEMBER)
                       .requestMatchers(antMatcher("/api/content/management/service/views/home")).permitAll()

                       .requestMatchers(antMatcher("/api/content/management/service/members/register")).permitAll()
                       .requestMatchers(antMatcher("/api/content/management/service/contents/getImage/**")).permitAll()
                       .anyRequest().authenticated())

                       .rememberMe(remember -> remember
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
        return (web -> { web.ignoring().requestMatchers( antMatcher("/css/**"));
                         web.ignoring().requestMatchers( antMatcher("/js/**"));
                         web.ignoring().requestMatchers( antMatcher("/templates/**"));
                         web.ignoring().requestMatchers( antMatcher("/images/**"));
                         web.ignoring().requestMatchers( antMatcher("/favicon.ico/**"));
        });
    }

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

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
