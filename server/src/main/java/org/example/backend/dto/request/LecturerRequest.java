package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.domain.Class;
import org.example.backend.domain.Faculty;

import java.util.List;

@Getter
@Setter
public class LecturerRequest {

    @Schema(description = "Full name of the lecturer", example = "Nguyen Van A")
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Schema(description = "Email address", example = "alice@example.com")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Phone number", example = "+84987654321")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @Schema(description = "Faculty ID", example = "1")
    @NotNull(message = "Faculty id is required")
    private Integer facultyId;

}
