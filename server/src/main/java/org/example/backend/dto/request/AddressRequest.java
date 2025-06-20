package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "AddressRequest", description = "Request payload to create or update an address")
public class AddressRequest {
    @Schema(description = "Type of address", example = "Thường Trú", requiredMode = Schema.RequiredMode.REQUIRED)
    private String addressType;

    @Schema(description = "House number and street name", example = "123 Lê Lợi", requiredMode = Schema.RequiredMode.REQUIRED)
    private String houseNumberStreetName;

    @Schema(description = "Ward or commune", example = "Phường 1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String wardCommune;

    @Schema(description = "District", example = "Quận 3", requiredMode = Schema.RequiredMode.REQUIRED)
    private String district;

    @Schema(description = "City or province", example = "TP.HCM", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cityProvince;

    @Schema(description = "Country", example = "Việt Nam", requiredMode = Schema.RequiredMode.REQUIRED)
    private String country;
}
