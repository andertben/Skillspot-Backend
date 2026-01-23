package de.skillspot.controller;

import de.skillspot.dto.CompleteProfileRequest;
import de.skillspot.dto.MeResponse;
import de.skillspot.dto.UpdateProfileRequest;
import de.skillspot.entity.BenutzerEntity;
import de.skillspot.service.BenutzerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MeController {

	private final BenutzerService benutzerService;

	public MeController(BenutzerService benutzerService) {
		this.benutzerService = benutzerService;
	}

	@GetMapping("/me")
	public ResponseEntity<MeResponse> getCurrentUser(Authentication authentication) {
		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			return ResponseEntity.status(401).build();
		}

		String sub = jwt.getSubject();
		String email = jwt.getClaimAsString("email");

		Optional<BenutzerEntity> benutzer = benutzerService.findByAuth0Sub(sub);

		MeResponse response = MeResponse.builder()
				.sub(sub)
				.email(email)
				.displayName(benutzer.map(BenutzerEntity::getDisplayName).orElse(null))
				.role(benutzer.map(BenutzerEntity::getRolle).orElse(null))
				.createdAt(benutzer.map(BenutzerEntity::getCreatedAt).orElse(null))
				.updatedAt(benutzer.map(BenutzerEntity::getUpdatedAt).orElse(null))
				.build();

		return ResponseEntity.ok(response);
	}

	@PostMapping("/me/complete-profile")
	public ResponseEntity<MeResponse> completeProfile(Authentication authentication, @Valid @RequestBody CompleteProfileRequest req) {
		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			return ResponseEntity.status(401).build();
		}

		String sub = jwt.getSubject();
		benutzerService.completeProfile(sub, req);

		String email = jwt.getClaimAsString("email");
		Optional<BenutzerEntity> benutzer = benutzerService.findByAuth0Sub(sub);

		MeResponse response = MeResponse.builder()
				.sub(sub)
				.email(email)
				.displayName(benutzer.map(BenutzerEntity::getDisplayName).orElse(null))
				.role(benutzer.map(BenutzerEntity::getRolle).orElse(null))
				.createdAt(benutzer.map(BenutzerEntity::getCreatedAt).orElse(null))
				.updatedAt(benutzer.map(BenutzerEntity::getUpdatedAt).orElse(null))
				.build();

		return ResponseEntity.ok(response);
	}

	@PutMapping("/me/profile")
	public ResponseEntity<MeResponse> updateProfile(Authentication authentication, @Valid @RequestBody UpdateProfileRequest req) {
		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			return ResponseEntity.status(401).build();
		}

		String sub = jwt.getSubject();
		benutzerService.updateProfile(sub, req);

		String email = jwt.getClaimAsString("email");
		Optional<BenutzerEntity> benutzer = benutzerService.findByAuth0Sub(sub);

		MeResponse response = MeResponse.builder()
				.sub(sub)
				.email(email)
				.displayName(benutzer.map(BenutzerEntity::getDisplayName).orElse(null))
				.role(benutzer.map(BenutzerEntity::getRolle).orElse(null))
				.createdAt(benutzer.map(BenutzerEntity::getCreatedAt).orElse(null))
				.updatedAt(benutzer.map(BenutzerEntity::getUpdatedAt).orElse(null))
				.build();

		return ResponseEntity.ok(response);
	}
}
