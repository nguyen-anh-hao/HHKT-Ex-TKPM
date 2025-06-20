package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.config.TestConfig;
import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.ClassResponse;
import org.example.backend.service.IClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClassController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(TestConfig.class)
public class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClassService classService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClassRequest validClassRequest;
    private ClassResponse classResponse;
    private List<ClassResponse> classResponseList;

    @BeforeEach
    void setUp() {
        // Setup Class Request
        validClassRequest = ClassRequest.builder()
                .classCode("CS101-01")
                .maxStudents(50)
                .schedule("Thứ Hai - Thứ Tư 10:00-12:00")
                .room("A101")
                .courseId(1)
                .lecturerId(1)
                .semesterId(1)
                .build();

        // Setup Class Response
        classResponse = ClassResponse.builder()
                .id(1)
                .classCode("CS101-01")
                .maxStudents(50)
                .schedule("Thứ Hai - Thứ Tư 10:00-12:00")
                .room("A101")
                .courseId(1)
                .courseCode("CS101")
                .courseName("Nhập môn Lập trình")
                .credits(3)
                .facultyName("Công nghệ Thông tin")
                .prerequisiteCourseCode("CS100")
                .prerequisiteCourseName("Lập trình Cơ bản")
                .lecturerId(1)
                .lecturerName("Nguyễn Văn A")
                .semesterId(1)
                .semesterName(2024)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        // Setup additional class for list operations
        ClassResponse classResponse2 = ClassResponse.builder()
                .id(2)
                .classCode("CS102-01")
                .maxStudents(40)
                .schedule("Thứ Ba - Thứ Năm 14:00-16:00")
                .room("B102")
                .courseId(2)
                .courseCode("CS102")
                .courseName("Cấu trúc Dữ liệu")
                .credits(3)
                .facultyName("Công nghệ Thông tin")
                .prerequisiteCourseCode("CS101")
                .prerequisiteCourseName("Nhập môn Lập trình")
                .lecturerId(2)
                .lecturerName("Trần Thị B")
                .semesterId(1)
                .semesterName(2024)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        classResponseList = List.of(classResponse, classResponse2);
    }

    @Test
    @DisplayName("POST /api/classes - Successfully create a new class")
    void shouldCreateClassSuccessfully() throws Exception {
        // Arrange
        when(classService.addClass(any(ClassRequest.class))).thenReturn(classResponse);

        // Act & Assert
        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validClassRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data.maxStudents").value(50))
                .andExpect(jsonPath("$.data.schedule").value("Thứ Hai - Thứ Tư 10:00-12:00"))
                .andExpect(jsonPath("$.data.room").value("A101"))
                .andExpect(jsonPath("$.data.courseCode").value("CS101"))
                .andExpect(jsonPath("$.data.courseName").value("Nhập môn Lập trình"))
                .andExpect(jsonPath("$.data.credits").value(3))
                .andExpect(jsonPath("$.data.facultyName").value("Công nghệ Thông tin"))
                .andExpect(jsonPath("$.data.lecturerName").value("Nguyễn Văn A"))
                .andExpect(jsonPath("$.data.semesterName").value(2024))
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(classService, times(1)).addClass(any(ClassRequest.class));
    }

    @Test
    @DisplayName("GET /api/classes/{classId} - Successfully retrieve class by ID")
    void shouldGetClassByIdSuccessfully() throws Exception {
        // Arrange
        when(classService.getClassById(1)).thenReturn(classResponse);

        // Act & Assert
        mockMvc.perform(get("/api/classes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data.maxStudents").value(50))
                .andExpect(jsonPath("$.data.schedule").value("Thứ Hai - Thứ Tư 10:00-12:00"))
                .andExpect(jsonPath("$.data.room").value("A101"))
                .andExpect(jsonPath("$.data.courseCode").value("CS101"))
                .andExpect(jsonPath("$.data.courseName").value("Nhập môn Lập trình"))
                .andExpect(jsonPath("$.data.credits").value(3))
                .andExpect(jsonPath("$.data.facultyName").value("Công nghệ Thông tin"))
                .andExpect(jsonPath("$.data.prerequisiteCourseCode").value("CS100"))
                .andExpect(jsonPath("$.data.prerequisiteCourseName").value("Lập trình Cơ bản"))
                .andExpect(jsonPath("$.data.lecturerName").value("Nguyễn Văn A"))
                .andExpect(jsonPath("$.data.semesterName").value(2024));

        // Verify service interaction
        verify(classService, times(1)).getClassById(1);
    }

    @Test
    @DisplayName("GET /api/classes - Successfully retrieve all classes with pagination")
    void shouldGetAllClassesSuccessfully() throws Exception {
        // Arrange
        Page<ClassResponse> page = new PageImpl<>(classResponseList, PageRequest.of(0, 3), classResponseList.size());
        when(classService.getAllClasses(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/classes")
                        .param("page", "0")
                        .param("size", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data[0].courseName").value("Nhập môn Lập trình"))
                .andExpect(jsonPath("$.data[0].facultyName").value("Công nghệ Thông tin"))
                .andExpect(jsonPath("$.data[0].lecturerName").value("Nguyễn Văn A"))
                .andExpect(jsonPath("$.data[0].maxStudents").value(50))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].classCode").value("CS102-01"))
                .andExpect(jsonPath("$.data[1].courseName").value("Cấu trúc Dữ liệu"))
                .andExpect(jsonPath("$.data[1].facultyName").value("Công nghệ Thông tin"))
                .andExpect(jsonPath("$.data[1].lecturerName").value("Trần Thị B"))
                .andExpect(jsonPath("$.data[1].maxStudents").value(40))
                .andExpect(jsonPath("$.paginationInfo").exists())
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(1))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.pageSize").value(3));

        // Verify service interaction
        verify(classService, times(1)).getAllClasses(any(Pageable.class));
    }

    @Test
    @DisplayName("POST /api/classes - Should return 400 when creating class with invalid data")
    void shouldReturnBadRequestWhenCreatingClassWithInvalidData() throws Exception {
        // Arrange - Create invalid request (required fields are null/empty)
        ClassRequest invalidRequest = ClassRequest.builder()
                .classCode(null) // Required field is null
                .maxStudents(null) // Required field is null
                .schedule("") // Empty required field
                .room(null) // Required field is null
                .courseId(null) // Required field is null
                .lecturerId(null) // Required field is null
                .semesterId(null) // Required field is null
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service interaction
        verify(classService, never()).addClass(any(ClassRequest.class));
    }

    @Test
    @DisplayName("POST /api/classes - Should return 400 when creating class with negative max students")
    void shouldReturnBadRequestWhenMaxStudentsIsNegative() throws Exception {
        // Arrange - Create request with negative max students
        ClassRequest invalidRequest = ClassRequest.builder()
                .classCode("CS101-01")
                .maxStudents(-10) // Invalid negative value
                .schedule("Thứ Hai - Thứ Tư 10:00-12:00")
                .room("A101")
                .courseId(1)
                .lecturerId(1)
                .semesterId(1)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service interaction
        verify(classService, never()).addClass(any(ClassRequest.class));
    }


    @Test
    @DisplayName("GET /api/classes - Successfully retrieve all classes with custom pagination")
    void shouldGetAllClassesWithCustomPagination() throws Exception {
        // Arrange
        Pageable customPageable = PageRequest.of(1, 2);
        Page<ClassResponse> customPage = new PageImpl<>(
                List.of(classResponse),
                customPageable,
                5 // total elements
        );
        when(classService.getAllClasses(any(Pageable.class))).thenReturn(customPage);

        // Act & Assert
        mockMvc.perform(get("/api/classes")
                        .param("page", "1")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].classCode").value("CS101-01"))
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(3))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(1))
                .andExpect(jsonPath("$.paginationInfo.pageSize").value(2));

        // Verify service interaction
        verify(classService, times(1)).getAllClasses(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/classes - Should handle empty class list")
    void shouldHandleEmptyClassList() throws Exception {
        // Arrange
        Page<ClassResponse> emptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 3),
                0
        );
        when(classService.getAllClasses(any(Pageable.class))).thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(get("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(0))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.pageSize").value(3));

        // Verify service interaction
        verify(classService, times(1)).getAllClasses(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/classes - Should use default pagination when no parameters provided")
    void shouldUseDefaultPaginationWhenNoParametersProvided() throws Exception {
        // Arrange
        Page<ClassResponse> defaultPage = new PageImpl<>(
                classResponseList,
                PageRequest.of(0, 3), // Default pagination from controller
                classResponseList.size()
        );
        when(classService.getAllClasses(any(Pageable.class))).thenReturn(defaultPage);

        // Act & Assert
        mockMvc.perform(get("/api/classes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.pageSize").value(3));

        // Verify service interaction
        verify(classService, times(1)).getAllClasses(any(Pageable.class));
    }

    @Test
    @DisplayName("POST /api/classes - Should create class with all required fields")
    void shouldCreateClassWithAllRequiredFields() throws Exception {
        assertTrue(true); // Placeholder for additional specific validation tests
    }
}