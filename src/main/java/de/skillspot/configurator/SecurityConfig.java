package de.skillspot.configurator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // WICHTIG: ZUERST Swagger erlauben
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                        // .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        // .anyRequest().authenticated()
                )

                // WICHTIG: formLogin deaktivieren, sonst Login-Seite!
                .formLogin(form -> form.disable())

                // Optional
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
