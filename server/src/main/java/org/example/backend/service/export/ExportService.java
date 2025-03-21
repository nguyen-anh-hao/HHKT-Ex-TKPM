package org.example.backend.service.export;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

public interface ExportService {
    byte[] exportData(List<?> data) throws IOException;

    String getFileExtension();

    MediaType getMediaType();
}
