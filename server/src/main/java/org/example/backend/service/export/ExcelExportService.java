package org.example.backend.service.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.backend.dto.response.AddressResponse;
import org.example.backend.dto.response.DocumentResponse;
import org.example.backend.dto.response.StudentResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class ExcelExportService implements ExportService {

    @Override
    public byte[] exportData(List<?> data) throws IOException {
        if (data.isEmpty()) {
            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet("Data Export");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return out.toByteArray();
        }


        if (data.get(0) instanceof StudentResponse) {
            List<Column<StudentResponse>> columns = defaultStudentResponseColumns();
            return exportData((List<StudentResponse>) data, columns);
        }
        throw new IllegalArgumentException("Unsupported data type");
    }

    public <T> byte[] exportData(List<T> data, List<Column<T>> columns) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data Export");

        // Create header row dynamically
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            headerRow.createCell(i).setCellValue(columns.get(i).getHeader());
        }

        // Populate the sheet with data rows
        int rowIndex = 1;
        for (T item : data) {
            Row row = sheet.createRow(rowIndex++);
            for (int colIndex = 0; colIndex < columns.size(); colIndex++) {
                String cellValue = columns.get(colIndex).extractValue(item);
                row.createCell(colIndex).setCellValue(cellValue);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    private List<Column<StudentResponse>> defaultStudentResponseColumns() {
        return List.of(
                new Column<>("Student ID", StudentResponse::getStudentId),
                new Column<>("Full Name", StudentResponse::getFullName),
                new Column<>("DoB", response -> response.getDob().toString()),
                new Column<>("Gender", StudentResponse::getGender),
                new Column<>("Phone", StudentResponse::getPhone),
                new Column<>("Email", StudentResponse::getEmail),
                new Column<>("Faculty", StudentResponse::getFaculty),
                new Column<>("Intake", StudentResponse::getIntake),
                new Column<>("Program", StudentResponse::getProgram),
                new Column<>("Student Status", StudentResponse::getStudentStatus),
                new Column<>("Permanent Address", response -> formatAddress(response.getAddresses(), "Permanent")),
                new Column<>("Temporary Address", response -> formatAddress(response.getAddresses(), "Temporary")),
                new Column<>("Mailing Address", response -> formatAddress(response.getAddresses(), "Mailing")),
                new Column<>("ID Card", response -> formatDocument(response.getDocuments(), "ID Card")),
                new Column<>("Citizen Card", response -> formatDocument(response.getDocuments(), "Citizen Card")),
                new Column<>("Passport", response -> formatDocument(response.getDocuments(), "Passport"))
        );
    }

    private String formatAddress(List<AddressResponse> addressResponses, String type) {
        return addressResponses.stream()
                .filter(addressResponse -> addressResponse.getAddressType().equals(type))
                .map(addressResponse -> addressResponse.getHouseNumberStreetName() + ", "
                        + addressResponse.getWardCommune() + ", "
                        + addressResponse.getDistrict() + ", "
                        + addressResponse.getCityProvince() + ", "
                        + addressResponse.getCountry())
                .findFirst()
                .orElse("");
    }

    private String formatDocument(List<DocumentResponse> documentResponses, String type) {
        return documentResponses.stream()
                .filter(documentResponse -> documentResponse.getDocumentType().equals(type))
                .map(documentResponse -> documentResponse.getDocumentNumber() + ", "
                        + documentResponse.getIssuedDate() + ", "
                        + documentResponse.getIssuedBy())
                .findFirst()
                .orElse("");
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @RequiredArgsConstructor
    public static class Column<T> {
        @Getter
        private final String header;
        private final Function<T, String> valueExtractor;

        public String extractValue(T item) {
            return valueExtractor.apply(item);
        }
    }
}
