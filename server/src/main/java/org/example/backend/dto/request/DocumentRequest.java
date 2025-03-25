package org.example.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DocumentRequest {
    private String documentType;
    private String documentNumber;
    private LocalDate issuedDate;
    private LocalDate expiredDate;
    private String issuedBy;
    private String issuedCountry;
    private String note;
    private Boolean hasChip;
}
