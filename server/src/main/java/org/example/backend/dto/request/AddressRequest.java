package org.example.backend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressRequest {
    private String addressType;
    private String houseNumberStreetName;
    private String wardCommune;
    private String district;
    private String cityProvince;
    private String country;
}
