package com.boomzin.subscriptionhub.config.security;

import com.boomzin.subscriptionhub.common.exception.ResponseStatus;
import com.boomzin.subscriptionhub.common.response.ErrorApiResponse;
import com.boomzin.subscriptionhub.domain.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private Sha256PasswordEncoder passwordEncoder;
    private final String notAuthResp;
    private final UserService userService;

    @Autowired
    public SecurityConfig(Sha256PasswordEncoder passwordEncoder, ObjectMapper objectMapper, UserService userService) throws JsonProcessingException {
        this.passwordEncoder = passwordEncoder;
        this.notAuthResp = objectMapper.writeValueAsString(new ErrorApiResponse(ResponseStatus.NOT_AUTHENTICATED, "Not authenticated"));
        this.userService = userService;
    }



    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = new ProviderManager(new TokenAuthenticationProvider(userService), new BasicAuthenticationProvider(userService));


        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Без сессий
//                .authenticationProvider(new TokenAuthenticationProvider(userService))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(BASIC_PATH_V1 + "/login").permitAll()  // Доступ ко всем
                        .anyRequest().authenticated() // Все остальные — только авторизованные
                )
                .addFilterAfter(new AllAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);

        return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}