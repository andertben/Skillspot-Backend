package de.skillspot.configurator;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {

	private static final String EXPECTED_AUDIENCE = "https://skillspot-api";

	@Override
	public OAuth2TokenValidatorResult validate(Jwt jwt) {
		Collection<String> audienceClaims = jwt.getAudience();

		if (CollectionUtils.isEmpty(audienceClaims) || !audienceClaims.contains(EXPECTED_AUDIENCE)) {
			return OAuth2TokenValidatorResult.failure(new OAuth2Error(
					OAuth2ErrorCodes.INVALID_TOKEN,
					"The audience claim does not contain the expected audience",
					null
			));
		}

		return OAuth2TokenValidatorResult.success();
	}
}
