package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
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
        ExportService exportService = exportServiceFactory.getExportService(type);

        if (exportService == null) {
            return ResponseEntity.badRequest().body(null);
        }

        String exportFileName = (fileName != null && !fileName.trim().isEmpty()) ? fileName.trim() : "data_export";
        byte[] data = exportService.exportData(sinhVienService.getAllStudent(pageable).getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportService.getMediaType());
        headers.setContentDispositionFormData("attachment", exportFileName + "." + type);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @PostMapping("/import")
    public ResponseEntity<ApiResponse> importData(
            @RequestParam String type,
            @RequestParam("file")MultipartFile file
            ) throws IOException {

        ImportService importService = importServiceFactory.getImportService(type);
        if (importService == null) {
            return ResponseEntity.badRequest().body(ApiResponse.builder().status(400).message("Invalid file type").build());
        }

        byte[] data = file.getBytes();
        List<SinhVienRequest> sinhVienRequests = importService.importData(data, SinhVienRequest.class);

        List<SinhVienResponse> sinhVienResponses = sinhVienService.addStudents(sinhVienRequests);

        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .status(200)
                        .message("Success")
                        .data(sinhVienResponses)
                        .build()
                );
    }
}
