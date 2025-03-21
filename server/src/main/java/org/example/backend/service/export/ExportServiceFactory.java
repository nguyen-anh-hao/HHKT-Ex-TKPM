package org.example.backend.service.export;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExportServiceFactory {
    private final Map<String, ExportService> exportServices;

    public ExportServiceFactory(List<ExportService> exportServices) {
        this.exportServices = exportServices.stream()
                .collect(Collectors.toMap(ExportService::getFileExtension, Function.identity()));
    }

    public ExportService getExportService(String fileExtension) {
        return exportServices.getOrDefault(fileExtension, null);
    }
}
