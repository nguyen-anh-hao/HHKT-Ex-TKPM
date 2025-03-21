package org.example.backend.service.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JSONImportService implements ImportService {

    private final ObjectMapper objectMapper;

    @Override
    public <T> List<T> importData(byte[] data, Class<T> type) throws IOException {
        T[] objects = objectMapper.readValue(data, objectMapper.getTypeFactory().constructArrayType(type));
        return List.of(objects);
    }

    @Override
    public String getFileExtension() {
        return "json";
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_JSON;
    }
}
