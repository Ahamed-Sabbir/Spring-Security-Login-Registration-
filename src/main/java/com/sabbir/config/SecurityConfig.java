package com.sabbir.config;

import com.sabbir.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headerConfigurer -> headerConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(requestConfigurer -> requestConfigurer
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .requestMatchers(antMatcher("/login/**")).permitAll()
                        .requestMatchers(antMatcher("/user/registration/**")).permitAll()
                        .requestMatchers(antMatcher("/user/**")).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers(antMatcher("/admin/**")).hasAuthority("ROLE_ADMIN")
                        .anyRequest().fullyAuthenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("passwprd")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                )
                .userDetailsService(customUserDetailsService);
        return httpSecurity.build();
    }
}
