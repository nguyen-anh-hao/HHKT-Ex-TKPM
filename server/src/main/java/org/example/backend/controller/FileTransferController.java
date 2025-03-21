package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.SinhVienRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.SinhVienResponse;
import org.example.backend.service.ISinhVienService;
import org.example.backend.service.Import.ImportService;
import org.example.backend.service.Import.ImportServiceFactory;
import org.example.backend.service.export.ExportService;
import org.example.backend.service.export.ExportServiceFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/file-transfer")
@RequiredArgsConstructor
@Slf4j
public class FileTransferController {
    private final ExportServiceFactory exportServiceFactory;
    private final ImportServiceFactory importServiceFactory;
    private final ISinhVienService sinhVienService;

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(
            @RequestParam String type,
            @RequestParam(required = false) String fileName,
            Pageable pageable

    ) throws IOException {
        log.info("Exporting data to file: type={}, fileName={}", type, fileName);
        ExportService exportService = exportServiceFactory.getExportService(type);

        if (exportService == null) {
            return ResponseEntity.badRequest().body(null);
        }

        String exportFileName = (fileName != null && !fileName.trim().isEmpty()) ? fileName.trim() : "data_export";
        byte[] data = exportService.exportData(sinhVienService.getAllStudent(pageable).getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportService.getMediaType());
        headers.setContentDispositionFormData("attachment", exportFileName + "." + type);

        log.info("Successfully exported data to file with data size: {}", data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @PostMapping("/import")
    public ResponseEntity<ApiResponse> importData(
            @RequestParam String type,
            @RequestParam("file")MultipartFile file
            ) throws IOException {
        log.info("Importing data from file: type={}, fileName={}", type, file.getOriginalFilename());

        ImportService importService = importServiceFactory.getImportService(type);
        if (importService == null) {
            return ResponseEntity.badRequest().body(ApiResponse.builder().status(400).message("Invalid file type").build());
        }

        byte[] data = file.getBytes();
        List<SinhVienRequest> sinhVienRequests = importService.importData(data, SinhVienRequest.class);

        List<SinhVienResponse> sinhVienResponses = sinhVienService.addStudents(sinhVienRequests);

        log.info("Successfully imported data from file with data size: {}", data.length);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .status(200)
                        .message("Success")
                        .data(sinhVienResponses)
                        .build()
                );
    }
}
