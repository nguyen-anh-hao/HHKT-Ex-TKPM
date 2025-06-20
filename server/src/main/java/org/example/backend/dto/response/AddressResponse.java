package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "AddressResponse", description = "Response payload containing address details")
public class AddressResponse {
    @Schema(description = "Type of address", example = "Tạm Trú")
    private String addressType;

    @Schema(description = "House number and street name", example = "456 Trần Hưng Đạo")
    private String houseNumberStreetName;

    @Schema(description = "Ward or commune", example = "Phường 5")
    private String wardCommune;

    @Schema(description = "District", example = "Quận 1")
    private String district;

    @Schema(description = "City or province", example = "TP.HCM")
    private String cityProvince;

    @Schema(description = "Country", example = "Việt Nam")
    private String country;
}
