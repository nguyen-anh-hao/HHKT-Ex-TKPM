package org.example.backend.service.export;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.example.backend.domain.Class;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Course;
import org.example.backend.domain.Student;
import org.example.backend.dto.data.TranscriptData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranscriptPdfExportServiceTest {

    private TranscriptPdfExportService transcriptPdfExportService;

    @BeforeEach
    void setUp() {
        transcriptPdfExportService = new TranscriptPdfExportService();
    }

    @Test
    void exportData_WithValidTranscriptData_ShouldGeneratePdfFile() throws IOException {
        // Given
        List<TranscriptData> transcriptDataList = createSampleTranscriptData();

        // When
        byte[] result = transcriptPdfExportService.exportData(transcriptDataList);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify PDF content
        verifyPdfContent(result, transcriptDataList.get(0));
    }

    @Test
    void exportData_WithEmptyList_ShouldThrowException() {
        // Given
        List<TranscriptData> emptyData = Collections.emptyList();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transcriptPdfExportService.exportData(emptyData)
        );
        assertEquals("Invalid data for transcript export", exception.getMessage());
    }

    @Test
    void exportData_WithInvalidDataType_ShouldThrowException() {
        // Given
        List<String> invalidData = Arrays.asList("invalid", "data");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transcriptPdfExportService.exportData(invalidData)
        );
        assertEquals("Invalid data for transcript export", exception.getMessage());
    }

    @Test
    void exportData_WithStudentWithoutCompletedCourses_ShouldGeneratePdfWithEmptyCoursesSection() throws IOException {
        // Given
        Student student = createSampleStudent();
        TranscriptData transcriptData = TranscriptData.builder()
                .student(student)
                .completedRegistrations(Collections.emptyList())
                .gpa(0.0)
                .build();

        // When
        byte[] result = transcriptPdfExportService.exportData(Arrays.asList(transcriptData));

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify PDF is valid
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(result)) {
            PdfReader reader = new PdfReader(inputStream);
            String pdfContent = PdfTextExtractor.getTextFromPage(reader, 1);

            assertTrue(pdfContent.contains("BẢNG ĐIỂM SINH VIÊN"));
            assertTrue(pdfContent.contains("ST001"));
            assertTrue(pdfContent.contains("John Doe"));
            assertTrue(pdfContent.contains("0.00")); // GPA
            reader.close();
        }
    }

    @Test
    void getFileExtension_ShouldReturnPdf() {
        // When
        String extension = transcriptPdfExportService.getFileExtension();

        // Then
        assertEquals("pdf", extension);
    }

    @Test
    void getMediaType_ShouldReturnPdfMediaType() {
        // When
        MediaType mediaType = transcriptPdfExportService.getMediaType();

        // Then
        assertEquals(MediaType.APPLICATION_PDF, mediaType);
    }

    @Test
    void exportData_WithMultipleCompletedCourses_ShouldIncludeAllCourses() throws IOException {
        // Given
        List<TranscriptData> transcriptDataList = createTranscriptDataWithMultipleCourses();

        // When
        byte[] result = transcriptPdfExportService.exportData(transcriptDataList);

        // Then
        assertNotNull(result);
        assertTrue(result.length > 0);

        // Verify all courses are included
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(result)) {
            PdfReader reader = new PdfReader(inputStream);
            String pdfContent = PdfTextExtractor.getTextFromPage(reader, 1);

            assertTrue(pdfContent.contains("Mathematics"));
            assertTrue(pdfContent.contains("Physics"));
            assertTrue(pdfContent.contains("Chemistry"));
            assertTrue(pdfContent.contains("8.50")); // Math grade
            assertTrue(pdfContent.contains("7.75")); // Physics grade
            assertTrue(pdfContent.contains("9.00")); // Chemistry grade
            reader.close();
        }
    }

    private List<TranscriptData> createSampleTranscriptData() {
        Student student = createSampleStudent();
        List<ClassRegistration> completedRegistrations = createSampleClassRegistrations();

        TranscriptData transcriptData = TranscriptData.builder()
                .student(student)
                .completedRegistrations(completedRegistrations)
                .gpa(8.42)
                .build();

        return Arrays.asList(transcriptData);
    }

    private List<TranscriptData> createTranscriptDataWithMultipleCourses() {
        Student student = createSampleStudent();

        Course mathCourse = Course.builder()
                .courseCode("MATH101")
                .courseName("Mathematics")
                .credits(3)
                .build();

        Course physicsCourse = Course.builder()
                .courseCode("PHYS101")
                .courseName("Physics")
                .credits(4)
                .build();

        Course chemistryCourse = Course.builder()
                .courseCode("CHEM101")
                .courseName("Chemistry")
                .credits(3)
                .build();

        Class mathClass = Class.builder()
                .classCode("MATH101-01")
                .course(mathCourse)
                .build();

        Class physicsClass = Class.builder()
                .classCode("PHYS101-01")
                .course(physicsCourse)
                .build();

        Class chemistryClass = Class.builder()
                .classCode("CHEM101-01")
                .course(chemistryCourse)
                .build();

        List<ClassRegistration> registrations = Arrays.asList(
                ClassRegistration.builder()
                        .student(student)
                        .aClass(mathClass)
                        .grade(8.5)
                        .build(),
                ClassRegistration.builder()
                        .student(student)
                        .aClass(physicsClass)
                        .grade(7.75)
                        .build(),
                ClassRegistration.builder()
                        .student(student)
                        .aClass(chemistryClass)
                        .grade(9.0)
                        .build()
        );

        TranscriptData transcriptData = TranscriptData.builder()
                .student(student)
                .completedRegistrations(registrations)
                .gpa(8.42)
                .build();

        return Arrays.asList(transcriptData);
    }

    private Student createSampleStudent() {
        return Student.builder()
                .studentId("ST001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 5, 15))
                .gender("Male")
                .intake("2022")
                .build();
    }

    private List<ClassRegistration> createSampleClassRegistrations() {
        Course course = Course.builder()
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .build();

        Class aClass = Class.builder()
                .classCode("CS101-01")
                .course(course)
                .build();

        ClassRegistration registration = ClassRegistration.builder()
                .student(createSampleStudent())
                .aClass(aClass)
                .grade(8.5)
                .build();

        return Arrays.asList(registration);
    }

    private void verifyPdfContent(byte[] pdfData, TranscriptData expectedData) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfData)) {
            PdfReader reader = new PdfReader(inputStream);
            String pdfContent = PdfTextExtractor.getTextFromPage(reader, 1);

            // Verify title
            assertTrue(pdfContent.contains("BẢNG ĐIỂM SINH VIÊN"));

            // Verify student information
            assertTrue(pdfContent.contains("1. Thông tin sinh viên"));
            assertTrue(pdfContent.contains(expectedData.getStudent().getStudentId()));
            assertTrue(pdfContent.contains(expectedData.getStudent().getFullName()));
            assertTrue(pdfContent.contains(expectedData.getStudent().getDob().toString()));
            assertTrue(pdfContent.contains(expectedData.getStudent().getGender()));
            assertTrue(pdfContent.contains(expectedData.getStudent().getIntake()));

            // Verify completed courses section
            assertTrue(pdfContent.contains("2. Danh sách môn học đã hoàn thành"));
            if (!expectedData.getCompletedRegistrations().isEmpty()) {
                ClassRegistration firstRegistration = expectedData.getCompletedRegistrations().get(0);
                assertTrue(pdfContent.contains(firstRegistration.getAClass().getClassCode()));
                assertTrue(pdfContent.contains(firstRegistration.getAClass().getCourse().getCourseName()));
                assertTrue(pdfContent.contains(String.format("%.2f", firstRegistration.getGrade())));
            }

            // Verify GPA section
            assertTrue(pdfContent.contains("3. Điểm trung bình tích lũy (GPA)"));
            assertTrue(pdfContent.contains(String.format("%.2f", expectedData.getGpa())));

            reader.close();
        }
    }
}