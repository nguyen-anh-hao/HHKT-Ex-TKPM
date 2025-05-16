package org.example.backend.mapper;

import org.example.backend.domain.Address;
import org.example.backend.dto.request.AddressRequest;
import org.example.backend.dto.response.AddressResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddressMapper {
    public static Address mapToDomain(AddressRequest addressRequest) {
        return Address.builder()
                .addressType(addressRequest.getAddressType())
                .houseNumberStreetName(addressRequest.getHouseNumberStreetName())
                .wardCommune(addressRequest.getWardCommune())
                .district(addressRequest.getDistrict())
                .cityProvince(addressRequest.getCityProvince())
                .country(addressRequest.getCountry())
                .build();
    }

    public static AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .addressType(address.getAddressType())
                .houseNumberStreetName(address.getHouseNumberStreetName())
                .wardCommune(address.getWardCommune())
                .district(address.getDistrict())
                .cityProvince(address.getCityProvince())
                .country(address.getCountry())
                .build();
    }

    public static AddressResponse mapToResponseWithTranslation(
            Address address,
            Map<String, String> translations) {

        return AddressResponse.builder()
                .addressType(translations.getOrDefault("addressType", address.getAddressType()))
                .houseNumberStreetName(address.getHouseNumberStreetName())
                .wardCommune(translations.getOrDefault("wardCommune", address.getWardCommune()))
                .district(translations.getOrDefault("district", address.getDistrict()))
                .cityProvince(translations.getOrDefault("cityProvince", address.getCityProvince()))
                .country(translations.getOrDefault("country", address.getCountry()))
                .build();
    }
}
