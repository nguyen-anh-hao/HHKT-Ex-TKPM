package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressResponse {
    private String addressType;
    private String houseNumberStreetName;
    private String wardCommune;
    private String district;
    private String cityProvince;
    private String country;
}
