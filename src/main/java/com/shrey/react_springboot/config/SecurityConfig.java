package com.shrey.react_springboot.config;

import com.shrey.react_springboot.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths
                .allowedOrigins("http://localhost:3001") // Your React app URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
                .allowCredentials(true) // Allow credentials (cookies)
                .allowedHeaders("*") // Allow all headers
                .exposedHeaders("Authorization"); // Expose headers if needed
    }


    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers("/login", "/register").permitAll() // Allow login and register without authentication
                                .anyRequest().authenticated() // Other requests require authentication
                )
                // .formLogin(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/students", true))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Sessions are created when necessary
                )
                .build();
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                System.out.println(se.getSession().getId());
                se.getSession().setMaxInactiveInterval(60); // Set session timeout to 60 seconds (1 minute)
            }
        };
    }

    @Bean
    UserDetailsService userDetailsService() {
        return customUserDetailsService; // Use the custom UserDetailsService
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Use custom UserDetailsService
        // No password encoder configured
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
