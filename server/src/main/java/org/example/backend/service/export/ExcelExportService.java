package org.example.backend.service.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class ExcelExportService implements ExportService {

    @RequiredArgsConstructor
    public static class Column<T> {
        @Getter
        private final String header;
        private final Function<T, String> valueExtractor;

        public String extractValue(T item) {
            return valueExtractor.apply(item);
        }
    }

    @Override
    public byte[] exportData(List<?> data) throws IOException {
        return new byte[0];
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    }
}
