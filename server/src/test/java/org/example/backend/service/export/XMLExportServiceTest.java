package org.example.backend.service.export;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.dto.response.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XMLExportServiceTest {

    private XMLExportService xmlExportService;
    private XmlMapper xmlMapper;

    @BeforeEach
    void setUp() {
        xmlExportService = new XMLExportService();
        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void exportData_WithValidStudentData_ShouldGenerateXmlFile() throws IOException {
        // Given
        List<StudentResponse> studentData = createSampleStudentData();

        // When
        byte[] result = xmlExportService.exportData(studentData);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify XML content
        String xmlContent = new String(result);
        assertTrue(xmlContent.contains("ST001"));
        assertTrue(xmlContent.contains("John Doe"));
        assertTrue(xmlContent.contains("john.doe@example.com"));
        assertTrue(xmlContent.contains("Computer Science"));
    }

    @Test
    void exportData_WithEmptyList_ShouldReturnEmptyXmlRoot() throws IOException {
        // Given
        List<StudentResponse> emptyData = Collections.emptyList();

        // When
        byte[] result = xmlExportService.exportData(emptyData);

        // Then
        assertNotNull(result);
        assertEquals("<root></root>", new String(result));
    }

    @Test
    void exportData_WithNullData_ShouldReturnEmptyXmlRoot() throws IOException {
        // Given
        List<StudentResponse> nullData = null;

        // When
        byte[] result = xmlExportService.exportData(nullData);

        // Then
        assertNotNull(result);
        assertEquals("<root></root>", new String(result));
    }

    @Test
    void exportData_WithSingleStudent_ShouldGenerateValidXml() throws IOException {
        // Given
        StudentResponse singleStudent = StudentResponse.builder()
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
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .build();

        List<StudentResponse> singleStudentList = Arrays.asList(singleStudent);

        // When
        byte[] result = xmlExportService.exportData(singleStudentList);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        String xmlContent = new String(result);
        assertTrue(xmlContent.contains("<studentId>ST001</studentId>"));
        assertTrue(xmlContent.contains("<fullName>John Doe</fullName>"));
        assertTrue(xmlContent.contains("<email>john.doe@example.com</email>"));
        assertTrue(xmlContent.contains("<faculty>Computer Science</faculty>"));
    }

    @Test
    void exportData_WithComplexStudentData_ShouldHandleAllFields() throws IOException {
        // Given
        List<StudentResponse> complexStudentData = createComplexStudentData();

        // When
        byte[] result = xmlExportService.exportData(complexStudentData);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        String xmlContent = new String(result);

        // Verify all fields are present
        assertTrue(xmlContent.contains("ST001"));
        assertTrue(xmlContent.contains("John Doe"));
        assertTrue(xmlContent.contains("Male"));
        assertTrue(xmlContent.contains("0123456789"));
        assertTrue(xmlContent.contains("john.doe@example.com"));
        assertTrue(xmlContent.contains("Computer Science"));
        assertTrue(xmlContent.contains("2022"));
        assertTrue(xmlContent.contains("Bachelor of Computer Science"));
        assertTrue(xmlContent.contains("Active"));

        // Verify nested objects (addresses and documents) are included
        assertTrue(xmlContent.contains("addresses"));
        assertTrue(xmlContent.contains("documents"));
    }

    @Test
    void exportData_WithDifferentDataTypes_ShouldHandleGenericTypes() throws IOException {
        // Given
        List<String> stringData = Arrays.asList("test1", "test2", "test3");

        // When
        byte[] result = xmlExportService.exportData(stringData);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        String xmlContent = new String(result);
        assertTrue(xmlContent.contains("test1"));
        assertTrue(xmlContent.contains("test2"));
        assertTrue(xmlContent.contains("test3"));

        // Verify it's valid XML
        List<String> parsedData = xmlMapper.readValue(result,
                xmlMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        assertEquals(3, parsedData.size());
        assertEquals("test1", parsedData.get(0));
    }

    @Test
    void getFileExtension_ShouldReturnXml() {
        // When
        String extension = xmlExportService.getFileExtension();

        // Then
        assertEquals("xml", extension);
    }

    @Test
    void getMediaType_ShouldReturnXmlMediaType() {
        // When
        MediaType mediaType = xmlExportService.getMediaType();

        // Then
        assertEquals(MediaType.APPLICATION_XML, mediaType);
    }

    private List<StudentResponse> createSampleStudentData() {
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
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
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
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .build();

        return Arrays.asList(student1, student2);
    }

    private List<StudentResponse> createComplexStudentData() {
        StudentResponse student = StudentResponse.builder()
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
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .build();

        return Arrays.asList(student);
    }
}