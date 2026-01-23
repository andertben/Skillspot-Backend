package de.skillspot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "Display Name is required")
    private String displayName;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "USER|PROVIDER", message = "Role must be USER or PROVIDER")
    private String role;

    private String address;
}
