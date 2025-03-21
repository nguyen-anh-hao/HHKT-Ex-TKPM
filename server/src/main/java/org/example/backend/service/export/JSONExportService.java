package org.example.backend.service.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JSONExportService implements ExportService {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] exportData(List<?> data) throws IOException {
        return objectMapper.writeValueAsBytes(data);
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
