package org.example.backend.service.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.dto.response.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JSONImportServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private JSONImportService jsonImportService;

    private byte[] testJsonData;
    private StudentResponse[] testStudentArray;
    private List<StudentResponse> expectedStudentList;

    @BeforeEach
    void setUp() {
        setupTestData();
    }

    private void setupTestData() {
        // Create test JSON data as byte array
        testJsonData = """
                [
                    {
                        "studentId": "ST001",
                        "fullName": "John Doe",
                        "dob": "2000-05-15",
                        "gender": "Male",
                        "phone": "0123456789",
                        "email": "john.doe@student.edu",
                        "faculty": "Computer Science",
                        "intake": "2022",
                        "program": "Bachelor of Computer Science",
                        "studentStatus": "Active"
                    },
                    {
                        "studentId": "ST002",
                        "fullName": "Jane Smith",
                        "dob": "1999-08-22",
                        "gender": "Female",
                        "phone": "0987654321",
                        "email": "jane.smith@student.edu",
                        "faculty": "Business Administration",
                        "intake": "2021",
                        "program": "Bachelor of Business Administration",
                        "studentStatus": "Active"
                    }
                ]
                """.getBytes();

        // Create expected student objects
        StudentResponse student1 = StudentResponse.builder()
                .studentId("ST001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 5, 15))
                .gender("Male")
                .phone("0123456789")
                .email("john.doe@student.edu")
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
                .email("jane.smith@student.edu")
                .faculty("Business Administration")
                .intake("2021")
                .program("Bachelor of Business Administration")
                .studentStatus("Active")
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .build();

        testStudentArray = new StudentResponse[]{student1, student2};
        expectedStudentList = List.of(student1, student2);
    }

    @Test
    void importData_WithValidJsonData_ShouldReturnListOfStudents() throws IOException {
        // Given
        when(objectMapper.getTypeFactory()).thenReturn(new ObjectMapper().getTypeFactory());
        when(objectMapper.readValue(eq(testJsonData), any(com.fasterxml.jackson.databind.JavaType.class)))
                .thenReturn(testStudentArray);

        // When
        List<StudentResponse> result = jsonImportService.importData(testJsonData, StudentResponse.class);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        StudentResponse firstStudent = result.get(0);
        assertEquals("ST001", firstStudent.getStudentId());
        assertEquals("John Doe", firstStudent.getFullName());
        assertEquals(LocalDate.of(2000, 5, 15), firstStudent.getDob());
        assertEquals("Male", firstStudent.getGender());
        assertEquals("john.doe@student.edu", firstStudent.getEmail());
        assertEquals("Computer Science", firstStudent.getFaculty());

        StudentResponse secondStudent = result.get(1);
        assertEquals("ST002", secondStudent.getStudentId());
        assertEquals("Jane Smith", secondStudent.getFullName());
        assertEquals(LocalDate.of(1999, 8, 22), secondStudent.getDob());
        assertEquals("Female", secondStudent.getGender());
        assertEquals("jane.smith@student.edu", secondStudent.getEmail());
        assertEquals("Business Administration", secondStudent.getFaculty());

        verify(objectMapper).readValue(eq(testJsonData), any(com.fasterxml.jackson.databind.JavaType.class));
    }

    @Test
    void importData_WithSingleStudentJson_ShouldReturnSingleItemList() throws IOException {
        // Given
        StudentResponse singleStudent = StudentResponse.builder()
                .studentId("ST003")
                .fullName("Alice Johnson")
                .dob(LocalDate.of(2001, 3, 10))
                .gender("Female")
                .phone("0555666777")
                .email("alice.johnson@student.edu")
                .faculty("Engineering")
                .intake("2023")
                .program("Bachelor of Engineering")
                .studentStatus("Active")
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .build();

        StudentResponse[] singleStudentArray = new StudentResponse[]{singleStudent};
        byte[] singleStudentJsonData = """
                [
                    {
                        "studentId": "ST003",
                        "fullName": "Alice Johnson",
                        "dob": "2001-03-10",
                        "gender": "Female",
                        "phone": "0555666777",
                        "email": "alice.johnson@student.edu",
                        "faculty": "Engineering",
                        "intake": "2023",
                        "program": "Bachelor of Engineering",
                        "studentStatus": "Active"
                    }
                ]
                """.getBytes();

        when(objectMapper.getTypeFactory()).thenReturn(new ObjectMapper().getTypeFactory());
        when(objectMapper.readValue(eq(singleStudentJsonData), any(com.fasterxml.jackson.databind.JavaType.class)))
                .thenReturn(singleStudentArray);

        // When
        List<StudentResponse> result = jsonImportService.importData(singleStudentJsonData, StudentResponse.class);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        StudentResponse resultStudent = result.get(0);
        assertEquals("ST003", resultStudent.getStudentId());
        assertEquals("Alice Johnson", resultStudent.getFullName());
        assertEquals(LocalDate.of(2001, 3, 10), resultStudent.getDob());
        assertEquals("Engineering", resultStudent.getFaculty());

        verify(objectMapper).readValue(eq(singleStudentJsonData), any(com.fasterxml.jackson.databind.JavaType.class));
    }

    @Test
    void importData_WithEmptyJsonArray_ShouldReturnEmptyList() throws IOException {
        // Given
        byte[] emptyJsonData = "[]".getBytes();
        StudentResponse[] emptyArray = new StudentResponse[0];

        when(objectMapper.getTypeFactory()).thenReturn(new ObjectMapper().getTypeFactory());
        when(objectMapper.readValue(eq(emptyJsonData), any(com.fasterxml.jackson.databind.JavaType.class)))
                .thenReturn(emptyArray);

        // When
        List<StudentResponse> result = jsonImportService.importData(emptyJsonData, StudentResponse.class);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(objectMapper).readValue(eq(emptyJsonData), any(com.fasterxml.jackson.databind.JavaType.class));
    }

    @Test
    void importData_WithDifferentDataType_ShouldWorkWithGenericType() throws IOException {
        // Given
        String[] stringArray = {"test1", "test2", "test3"};
        byte[] stringJsonData = "[\"test1\", \"test2\", \"test3\"]".getBytes();

        when(objectMapper.getTypeFactory()).thenReturn(new ObjectMapper().getTypeFactory());
        when(objectMapper.readValue(eq(stringJsonData), any(com.fasterxml.jackson.databind.JavaType.class)))
                .thenReturn(stringArray);

        // When
        List<String> result = jsonImportService.importData(stringJsonData, String.class);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("test1", result.get(0));
        assertEquals("test2", result.get(1));
        assertEquals("test3", result.get(2));

        verify(objectMapper).readValue(eq(stringJsonData), any(com.fasterxml.jackson.databind.JavaType.class));
    }

    @Test
    void importData_WithComplexStudentData_ShouldHandleAllFields() throws IOException {
        // Given
        StudentResponse complexStudent = StudentResponse.builder()
                .studentId("ST004")
                .fullName("Michael Brown")
                .dob(LocalDate.of(1998, 12, 25))
                .gender("Male")
                .phone("+84123456789")
                .email("michael.brown@student.edu")
                .faculty("Medicine")
                .intake("2020")
                .program("Doctor of Medicine")
                .studentStatus("Graduated")
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .build();

        StudentResponse[] complexStudentArray = new StudentResponse[]{complexStudent};
        byte[] complexJsonData = """
                [
                    {
                        "studentId": "ST004",
                        "fullName": "Michael Brown",
                        "dob": "1998-12-25",
                        "gender": "Male",
                        "phone": "+84123456789",
                        "email": "michael.brown@student.edu",
                        "faculty": "Medicine",
                        "intake": "2020",
                        "program": "Doctor of Medicine",
                        "studentStatus": "Graduated"
                    }
                ]
                """.getBytes();

        when(objectMapper.getTypeFactory()).thenReturn(new ObjectMapper().getTypeFactory());
        when(objectMapper.readValue(eq(complexJsonData), any(com.fasterxml.jackson.databind.JavaType.class)))
                .thenReturn(complexStudentArray);

        // When
        List<StudentResponse> result = jsonImportService.importData(complexJsonData, StudentResponse.class);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        StudentResponse resultStudent = result.get(0);
        assertEquals("ST004", resultStudent.getStudentId());
        assertEquals("Michael Brown", resultStudent.getFullName());
        assertEquals(LocalDate.of(1998, 12, 25), resultStudent.getDob());
        assertEquals("Male", resultStudent.getGender());
        assertEquals("+84123456789", resultStudent.getPhone());
        assertEquals("michael.brown@student.edu", resultStudent.getEmail());
        assertEquals("Medicine", resultStudent.getFaculty());
        assertEquals("2020", resultStudent.getIntake());
        assertEquals("Doctor of Medicine", resultStudent.getProgram());
        assertEquals("Graduated", resultStudent.getStudentStatus());

        verify(objectMapper).readValue(eq(complexJsonData), any(com.fasterxml.jackson.databind.JavaType.class));
    }

    @Test
    void getFileExtension_ShouldReturnJson() {
        // When
        String extension = jsonImportService.getFileExtension();

        // Then
        assertEquals("json", extension);
    }

    @Test
    void getMediaType_ShouldReturnApplicationJson() {
        // When
        MediaType mediaType = jsonImportService.getMediaType();

        // Then
        assertEquals(MediaType.APPLICATION_JSON, mediaType);
    }

    @Test
    void importData_ShouldUseCorrectTypeFactory() throws IOException {
        // Given
        when(objectMapper.getTypeFactory()).thenReturn(new ObjectMapper().getTypeFactory());
        when(objectMapper.readValue(eq(testJsonData), any(com.fasterxml.jackson.databind.JavaType.class)))
                .thenReturn(testStudentArray);

        // When
        jsonImportService.importData(testJsonData, StudentResponse.class);

        // Then
        verify(objectMapper).getTypeFactory();
        verify(objectMapper).readValue(eq(testJsonData), any(com.fasterxml.jackson.databind.JavaType.class));
    }
}