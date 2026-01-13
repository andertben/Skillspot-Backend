package de.skillspot.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

	@GetMapping("/me")
	public ResponseEntity<UserInfo> getCurrentUser(Authentication authentication) {
		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
			return ResponseEntity.status(401).build();
		}

		Jwt jwt = (Jwt) authentication.getPrincipal();

		String sub = jwt.getSubject();
		String email = jwt.getClaimAsString("email");
		String name = jwt.getClaimAsString("name");

		UserInfo userInfo = new UserInfo(sub, email, name);
		return ResponseEntity.ok(userInfo);
	}

	@Data
	public static class UserInfo {
		private String sub;
		private String email;
		private String name;

		public UserInfo(String sub, String email, String name) {
			this.sub = sub;
			this.email = email;
			this.name = name;
		}
	}
}
