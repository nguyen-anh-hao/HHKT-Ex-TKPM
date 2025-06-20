package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TranslateRequest {

    @Schema(description = "Text to be translated", example = "Hello, world!")
    private String q;

    @Schema(description = "Source language code", example = "en")
    private String source;

    @Schema(description = "Target language code", example = "es")
    private String target;
}
