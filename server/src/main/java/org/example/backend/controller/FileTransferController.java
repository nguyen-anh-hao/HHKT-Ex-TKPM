package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.service.IStudentService;
import org.example.backend.service.Import.ImportService;
import org.example.backend.service.Import.ImportServiceFactory;
import org.example.backend.service.export.ExportService;
import org.example.backend.service.export.ExportServiceFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    private final IStudentService studentService;

    @GetMapping("/export")
    @Operation(
            summary = "Export student data",
            description = "Export student data in a given file format (e.g., csv, xlsx)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File exported successfully",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "400", description = "Invalid export type",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<byte[]> exportData(
            @Parameter(description = "Type of export file (e.g., csv, xlsx)", required = true)
            @RequestParam String type,
            @RequestParam(required = false) String fileName,
            Pageable pageable

    ) throws IOException {
        log.info("Received request to export data: type={}, fileName={}, pageable={}", type, fileName, pageable);
        ExportService exportService = exportServiceFactory.getExportService(type);

        if (exportService == null) {
            return ResponseEntity.badRequest().body(null);
        }

        String exportFileName = (fileName != null && !fileName.trim().isEmpty()) ? fileName.trim() : "data_export";
        byte[] data = exportService.exportData(studentService.getAllStudents(pageable).getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportService.getMediaType());
        headers.setContentDispositionFormData("attachment", exportFileName + "." + type);

        log.info("Successfully exported data to file with data size: {}", data.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @PostMapping("/import")
    @Operation(
            summary = "Import student data",
            description = "Import student data from an uploaded file in a supported format (e.g., csv, xlsx)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data imported successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file type or content",
                    content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "type", description = "Type of file to import (e.g., csv, xlsx)", required = true, example = "csv"),
            @Parameter(name = "file", description = "File to upload", required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    })
    public ResponseEntity<APIResponse> importData(
            @RequestParam String type,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        log.info("Received request to import data: type={}, fileName={}", type, file.getOriginalFilename());

        ImportService importService = importServiceFactory.getImportService(type);
        if (importService == null) {
            return ResponseEntity.badRequest().body(APIResponse.builder().status(400).message("Invalid file type").build());
        }

        byte[] data = file.getBytes();
        List<StudentRequest> studentRequests = importService.importData(data, StudentRequest.class);

        List<StudentResponse> studentResponses = studentService.addStudents(studentRequests);

        log.info("Successfully imported data from file with data size: {}", data.length);

        return ResponseEntity.ok()
                .body(APIResponse.builder()
                        .status(200)
                        .message("Success")
                        .data(studentResponses)
                        .build()
                );
    }
}
