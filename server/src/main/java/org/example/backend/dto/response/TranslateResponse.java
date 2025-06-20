package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslateResponse {

    @Schema(description = "Translated text", example = "Hola, mundo!")
    private String translatedText;
}
