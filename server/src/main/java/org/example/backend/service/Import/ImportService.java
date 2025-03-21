package org.example.backend.service.Import;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

public interface ImportService {
    <T> List<T> importData(byte[] data, Class<T> type) throws IOException;

    String getFileExtension();

    MediaType getMediaType();
}
