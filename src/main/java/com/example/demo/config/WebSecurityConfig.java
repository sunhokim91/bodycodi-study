package com.example.demo.config;

import com.example.demo.domain.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {
    private static final String API_DOCS_USER_API_HTML = "/api/docs/index.html";
    private static final String LOGIN = "/api/login";

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles(Roles.ADMIN.name())
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionAuthenticationErrorUrl(LOGIN)
                .invalidSessionUrl(LOGIN)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(API_DOCS_USER_API_HTML).hasRole(Roles.ADMIN.name())
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage(LOGIN)
                .loginProcessingUrl(LOGIN)
                .defaultSuccessUrl(API_DOCS_USER_API_HTML)
                .permitAll();

        return http.build();
    }
}
