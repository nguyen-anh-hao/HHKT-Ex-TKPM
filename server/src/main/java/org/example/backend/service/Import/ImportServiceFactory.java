package org.example.backend.service.Import;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ImportServiceFactory {
    private final Map<String, ImportService> importServices;

    public ImportServiceFactory(List<ImportService> importServices) {
        this.importServices = importServices.stream()
                .collect(Collectors.toMap(ImportService::getFileExtension, Function.identity()));
    }

    public ImportService getImportService(String fileExtension) {
        return importServices.get(fileExtension);
    }
}
