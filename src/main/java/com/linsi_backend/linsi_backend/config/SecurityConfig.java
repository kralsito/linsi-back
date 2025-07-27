package com.linsi_backend.linsi_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    private static String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                // HABILITAMOS CORS en lugar de deshabilitarlo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        httpSecurity.authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests
                        // Agregamos endpoints para debugging
                        .requestMatchers("/", "/health", "/api/status").permitAll()
                        // Endpoints de autenticación
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        // Tus endpoints existentes
                        .requestMatchers("/role/**", "/role").permitAll()
                        .requestMatchers("/member/**", "/member").permitAll()
                        .requestMatchers("/area", "/area/**").permitAll()
                        .requestMatchers("/areaxmember", "/areaxmember/**").permitAll()
                        .requestMatchers("/news", "/news/**").permitAll()
                        .requestMatchers("/project", "/project/**").permitAll()
                        .requestMatchers("/projectxmember", "/projectxmember/**").permitAll()
                        .requestMatchers("/projectxarea", "/projectxarea/**").permitAll()
                        .requestMatchers("/registration").permitAll()
                        // Swagger
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        // Actuator para health checks
                        .requestMatchers("/actuator/**").permitAll()
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
        );

        httpSecurity.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:*",
                "https://*.vercel.app",
                "https://linsi.vercel.app",
                "https://linsi-back-production.up.railway.app"
        ));

        // Métodos HTTP permitidos
        corsConfiguration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        // Headers permitidos
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // Headers expuestos
        corsConfiguration.setExposedHeaders(Arrays.asList(
                "X-Total-Count",
                "Authorization"
        ));

        // Permitir credenciales
        corsConfiguration.setAllowCredentials(true);

        // Configurar para todos los paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}