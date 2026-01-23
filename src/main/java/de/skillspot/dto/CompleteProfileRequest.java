package de.skillspot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteProfileRequest {
    @NotBlank(message = "Display Name is required")
    private String displayName;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "USER|PROVIDER", message = "Role must be USER or PROVIDER")
    @JsonAlias({"rolle"})
    private String role;

    private BigDecimal locationLat;
    private BigDecimal locationLon;
}
