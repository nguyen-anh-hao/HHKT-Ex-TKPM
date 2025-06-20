package org.example.backend.service.export;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.backend.dto.response.AddressResponse;
import org.example.backend.dto.response.DocumentResponse;
import org.example.backend.dto.response.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcelExportServiceTest {

    private ExcelExportService excelExportService;

    @BeforeEach
    void setUp() {
        excelExportService = new ExcelExportService();
    }

    @Test
    void exportData_WithValidStudentData_ShouldGenerateExcelFile() throws IOException {
        // Given
        List<StudentResponse> studentData = createSampleStudentData();

        // When
        byte[] result = excelExportService.exportData(studentData);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify Excel content
        verifyExcelContent(result, studentData);
    }

    @Test
    void exportData_WithEmptyList_ShouldGenerateEmptyExcelFile() throws IOException {
        // Given
        List<StudentResponse> emptyData = Collections.emptyList();

        // When
        byte[] result = excelExportService.exportData(emptyData);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify it's a valid Excel file
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
            assertEquals(1, workbook.getNumberOfSheets());
        }
    }

    @Test
    void exportData_WithUnsupportedDataType_ShouldThrowException() {
        // Given
        List<String> unsupportedData = Arrays.asList("test1", "test2");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> excelExportService.exportData(unsupportedData)
        );
        assertEquals("Unsupported data type", exception.getMessage());
    }

    @Test
    void exportData_WithCustomColumns_ShouldGenerateExcelWithCustomHeaders() throws IOException {
        // Given
        List<StudentResponse> studentData = createSampleStudentData();
        List<ExcelExportService.Column<StudentResponse>> customColumns = Arrays.asList(
                new ExcelExportService.Column<>("ID", StudentResponse::getStudentId),
                new ExcelExportService.Column<>("Name", StudentResponse::getFullName),
                new ExcelExportService.Column<>("Email", StudentResponse::getEmail)
        );

        // When
        byte[] result = excelExportService.exportData(studentData, customColumns);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify custom headers
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            assertEquals("ID", headerRow.getCell(0).getStringCellValue());
            assertEquals("Name", headerRow.getCell(1).getStringCellValue());
            assertEquals("Email", headerRow.getCell(2).getStringCellValue());
            assertEquals(3, headerRow.getLastCellNum());
        }
    }

    @Test
    void getFileExtension_ShouldReturnXlsx() {
        // When
        String extension = excelExportService.getFileExtension();

        // Then
        assertEquals("xlsx", extension);
    }

    @Test
    void getMediaType_ShouldReturnExcelMediaType() {
        // When
        MediaType mediaType = excelExportService.getMediaType();

        // Then
        assertEquals(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                mediaType);
    }

    @Test
    void column_ShouldExtractValueCorrectly() {
        // Given
        StudentResponse student = createSampleStudentData().get(0);
        ExcelExportService.Column<StudentResponse> column =
                new ExcelExportService.Column<>("Student ID", StudentResponse::getStudentId);

        // When
        String extractedValue = column.extractValue(student);

        // Then
        assertEquals("ST001", extractedValue);
        assertEquals("Student ID", column.getHeader());
    }

    private List<StudentResponse> createSampleStudentData() {
        List<AddressResponse> addresses = Arrays.asList(
                AddressResponse.builder()
                        .addressType("Permanent")
                        .houseNumberStreetName("123 Main St")
                        .wardCommune("Ward 1")
                        .district("District 1")
                        .cityProvince("Ho Chi Minh City")
                        .country("Vietnam")
                        .build(),
                AddressResponse.builder()
                        .addressType("Temporary")
                        .houseNumberStreetName("456 Temp St")
                        .wardCommune("Ward 2")
                        .district("District 2")
                        .cityProvince("Ho Chi Minh City")
                        .country("Vietnam")
                        .build()
        );

        List<DocumentResponse> documents = Arrays.asList(
                DocumentResponse.builder()
                        .documentType("ID Card")
                        .documentNumber("123456789")
                        .issuedDate(LocalDate.of(2020, 1, 15))
                        .issuedBy("Police Department")
                        .build(),
                DocumentResponse.builder()
                        .documentType("Passport")
                        .documentNumber("P123456789")
                        .issuedDate(LocalDate.of(2021, 6, 20))
                        .issuedBy("Immigration Office")
                        .build()
        );

        StudentResponse student1 = StudentResponse.builder()
                .studentId("ST001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 5, 15))
                .gender("Male")
                .phone("0123456789")
                .email("john.doe@example.com")
                .faculty("Computer Science")
                .intake("2022")
                .program("Bachelor of Computer Science")
                .studentStatus("Active")
                .addresses(addresses)
                .documents(documents)
                .build();

        StudentResponse student2 = StudentResponse.builder()
                .studentId("ST002")
                .fullName("Jane Smith")
                .dob(LocalDate.of(1999, 8, 22))
                .gender("Female")
                .phone("0987654321")
                .email("jane.smith@example.com")
                .faculty("Business Administration")
                .intake("2021")
                .program("Bachelor of Business Administration")
                .studentStatus("Active")
                .addresses(new ArrayList<>())
                .documents(new ArrayList<>())
                .build();

        return Arrays.asList(student1, student2);
    }

    private void verifyExcelContent(byte[] excelData, List<StudentResponse> expectedData) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelData))) {
            Sheet sheet = workbook.getSheetAt(0);

            // Verify sheet name
            assertEquals("Data Export", sheet.getSheetName());

            // Verify header row
            Row headerRow = sheet.getRow(0);
            assertNotNull(headerRow);
            assertEquals("Student ID", headerRow.getCell(0).getStringCellValue());
            assertEquals("Full Name", headerRow.getCell(1).getStringCellValue());
            assertEquals("Email", headerRow.getCell(5).getStringCellValue());

            // Verify data rows
            assertEquals(expectedData.size() + 1, sheet.getLastRowNum() + 1); // +1 for header

            // Verify first student data
            Row firstDataRow = sheet.getRow(1);
            assertNotNull(firstDataRow);
            assertEquals("ST001", firstDataRow.getCell(0).getStringCellValue());
            assertEquals("John Doe", firstDataRow.getCell(1).getStringCellValue());
            assertEquals("john.doe@example.com", firstDataRow.getCell(5).getStringCellValue());

            // Verify second student data
            Row secondDataRow = sheet.getRow(2);
            assertNotNull(secondDataRow);
            assertEquals("ST002", secondDataRow.getCell(0).getStringCellValue());
            assertEquals("Jane Smith", secondDataRow.getCell(1).getStringCellValue());
            assertEquals("jane.smith@example.com", secondDataRow.getCell(5).getStringCellValue());
        }
    }
}