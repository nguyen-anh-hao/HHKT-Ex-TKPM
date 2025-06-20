package org.example.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.backend.common.PaginationInfo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {

    @Schema(description = "Result message", example = "Success")
    private String message;

    @Schema(description = "HTTP status code", example = "200")
    private int status;


    @Schema(description = "Response data")
    private Object data;

    // if it is null then do not include it in the response
    @Schema(description = "Pagination info (if paged)")
    private PaginationInfo paginationInfo;
}
