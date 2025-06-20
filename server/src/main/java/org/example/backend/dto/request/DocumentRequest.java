package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DocumentRequest {

    @Schema(description = "Type of the document", example = "CCCD")
    private String documentType;

    @Schema(description = "Document number", example = "123456789012")
    private String documentNumber;

    @Schema(description = "Date the document was issued", example = "2020-10-01")
    private LocalDate issuedDate;

    @Schema(description = "Date the document expires", example = "2030-10-01")
    private LocalDate expiredDate;

    @Schema(description = "Authority that issued the document", example = "Công an tỉnh")
    private String issuedBy;

    @Schema(description = "Country where the document was issued", example = "Việt Nam")
    private String issuedCountry;

    @Schema(description = "Additional notes about the document", example = "This is a sample note.")
    private String note;

    @Schema(description = "Indicates if the document has a chip", example = "true")
    private Boolean hasChip;
}
