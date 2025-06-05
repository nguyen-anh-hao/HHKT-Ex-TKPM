package org.example.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TranslateRequest {
    private String q;
    private String source;
    private String target;
}
