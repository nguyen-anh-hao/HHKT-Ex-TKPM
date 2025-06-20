package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDomainRequest {

    @Schema(description = "Domain name for email addresses", example = "example.com")
    @NotBlank(message = "Domain is required")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9-]*(\\.[a-zA-Z0-9][a-zA-Z0-9-]*)+$",
            message = "Invalid domain format")
    private String domain;
}
