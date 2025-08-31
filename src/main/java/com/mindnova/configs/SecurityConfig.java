package com.mindnova.configs;

import com.mindnova.security.JpaUserDetailService;
import com.mindnova.security.jwt.authentication.JwtAuthenticationFilter;
import com.mindnova.security.jwt.authentication.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JpaUserDetailService jpaUserDetailService, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/posts/**"
                        ).permitAll()
                        // admin APIs
                        .requestMatchers("/api/users/**").hasAuthority("ADMIN")

                        // user APIs (bệnh nhân)
                        .requestMatchers("/api/emotion-journals/me").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, "/api/appointments/**").hasAuthority("USER") // đặt lịch

                        .requestMatchers("/api/profile/me").hasAnyAuthority("USER", "DOCTOR")

                        // doctor APIs
                        .requestMatchers("/api/appointments/doctor/**").hasAuthority("DOCTOR") // quản lý lịch làm việc
                        .requestMatchers("/api/doctor-schedules/me").hasAuthority("DOCTOR")

                        // everything else
                        .anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .userDetailsService(jpaUserDetailService)
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class) // tat ca phai o sau logout filter
        ;
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception {
        return new JwtAuthenticationFilter(jwtAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
