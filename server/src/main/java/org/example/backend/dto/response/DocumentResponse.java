package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DocumentResponse {

    @Schema(description = "Type of the document", example = "Hộ chiếu")
    private String documentType;

    @Schema(description = "Unique identifier for the document", example = "123456789")
    private String documentNumber;

    @Schema(description = "Date the document was issued", example = "2019-06-15")
    private LocalDate issuedDate;

    @Schema(description = "Date the document will expire", example = "2029-06-15")
    private LocalDate expiredDate;

    @Schema(description = "Name of the person to whom the document was issued", example = "Nguyễn Văn A")
    private String issuedBy;

    @Schema(description = "Country where the document was issued", example = "Việt Nam")
    private String issuedCountry;

    @Schema(description = "Additional notes about the document", example = "This is a sample note.")
    private String note;

    @Schema(description = "Indicates if the document has a chip", example = "true")
    private Boolean hasChip;

}
