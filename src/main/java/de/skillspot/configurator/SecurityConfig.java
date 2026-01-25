package de.skillspot.configurator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

	private static final List<String> FRONTEND_ORIGINS = List.of(
			"http://localhost:5173",
			"http://188.245.196.16:8081"
	);

	private static final String[] PUBLIC_ENDPOINTS = {
			"/kategorien/**",
			"/dienstleistungen",
			"/anbieter",
			"/reviews",
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/swagger-ui.html"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(org.springframework.http.HttpMethod.GET, "/dienstleistungen").permitAll()
						.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
						.anyRequest().authenticated()
				)
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> {})
						.authenticationEntryPoint((request, response, authException) -> {
							System.err.println("Auth failure: " + authException.getMessage());
							response.sendError(401, authException.getMessage());
						})
				)
				.formLogin(form -> form.disable())
				.httpBasic(httpBasic -> httpBasic.disable());
		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
				.withIssuerLocation("https://dev-cuabf3ql66715pfn.us.auth0.com/")
				.build();

		jwtDecoder.setJwtValidator(
				new org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator(
						new org.springframework.security.oauth2.jwt.JwtTimestampValidator(),
						new JwtAudienceValidator()
				)
		);

		return jwtDecoder;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(FRONTEND_ORIGINS);
		config.setAllowedMethods(List.of(
				"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
		));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
