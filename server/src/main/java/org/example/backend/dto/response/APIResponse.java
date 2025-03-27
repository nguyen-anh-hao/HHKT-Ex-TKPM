package org.example.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.example.backend.common.PaginationInfo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private String message;
    private int status;
    private Object data;
    // if it is null then do not include it in the response
    private PaginationInfo paginationInfo;
}
