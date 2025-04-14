package org.example.backend.service;

import org.example.backend.domain.Faculty;
import org.example.backend.domain.Program;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private IStudentRepository studentRepository;

    @Mock
    private IProgramRepository  programRepository;

    @Mock
    private IFacultyRepository facultyRepository;

    @Mock
    private IStudentStatusRepository studentStatusRepository;

    @Test
    @DisplayName("should add student")
    void shouldAddStudent() {
        // Arrange
        StudentRequest request = StudentRequest.builder()
                .studentId("SV001")
                .facultyId(1)
                .programId(2)
                .studentStatusId(3)
                .fullName("John Doe")
                .build();

        when(studentRepository.findByStudentId("SV001")).thenReturn(Optional.empty());
        when(facultyRepository.findById(1)).thenReturn(Optional.of(new Faculty()));
        when(programRepository.findById(2)).thenReturn(Optional.of(new Program()));
        when(studentStatusRepository.findById(3)).thenReturn(Optional.of(new StudentStatus()));

        Student savedStudent = Student.builder()
                .studentId("SV001")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .fullName("John Doe")
                .build();

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // Act
        StudentResponse response = studentService.addStudent(request);

        // Assert
        assertThat(response.getStudentId()).isEqualTo("SV001");
        assertThat(response.getFullName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("should throw exception when student id already exists")
    void showThrowExceptionWhenStudentIdAlreadyExists() {
        // Arrange
        StudentRequest request = StudentRequest.builder()
                .studentId("SV001")
                .facultyId(1)
                .programId(2)
                .studentStatusId(3)
                .fullName("John Doe")
                .build();

        when(studentRepository.findByStudentId("SV001")).thenReturn(Optional.of(new Student()));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.addStudent(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Student id already exists");
    }

    @Test
    @DisplayName("should throw exception when faculty not found")
    void shouldThrowExceptionWhenFacultyNotFound() {
        // Arrange
        StudentRequest request = StudentRequest.builder()
                .studentId("SV001")
                .facultyId(1)
                .programId(2)
                .studentStatusId(3)
                .fullName("John Doe")
                .build();

        when(studentRepository.findByStudentId("SV001")).thenReturn(Optional.empty());
        when(facultyRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.addStudent(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Faculty not found");
    }

    @Test
    @DisplayName("should throw exception when program not found")
    void shouldThrowExceptionWhenProgramNotFound() {
        // Arrange
        StudentRequest request = StudentRequest.builder()
                .studentId("SV001")
                .facultyId(1)
                .programId(2)
                .studentStatusId(3)
                .fullName("John Doe")
                .build();

        when(studentRepository.findByStudentId("SV001")).thenReturn(Optional.empty());
        when(facultyRepository.findById(1)).thenReturn(Optional.of(new Faculty()));
        when(programRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.addStudent(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Program not found");
    }

    @Test
    @DisplayName("should throw exception when student status not found")
    void shouldThrowExceptionWhenStudentStatusNotFound() {
        // Arrange
        StudentRequest request = StudentRequest.builder()
                .studentId("SV001")
                .facultyId(1)
                .programId(2)
                .studentStatusId(3)
                .fullName("John Doe")
                .build();

        when(studentRepository.findByStudentId("SV001")).thenReturn(Optional.empty());
        when(facultyRepository.findById(1)).thenReturn(Optional.of(new Faculty()));
        when(programRepository.findById(2)).thenReturn(Optional.of(new Program()));
        when(studentStatusRepository.findById(3)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.addStudent(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Student status not found");
    }

    @Test
    @DisplayName("should get student by id")
    void shouldGetStudentById() {
        // Arrange
        String studentId = "SV001";
        Student student = Student.builder()
                .studentId(studentId)
                .fullName("John Doe")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(student));

        // Act
        StudentResponse response = studentService.getStudent(studentId);

        // Assert
        assertThat(response.getStudentId()).isEqualTo(studentId);
        assertThat(response.getFullName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("should throw exception when student not found")
    void shouldThrowExceptionWhenStudentNotFound() {
        // Arrange
        String studentId = "SV001";

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.getStudent(studentId);
        });

        assertThat(exception.getMessage()).isEqualTo("Student not found");
    }

    @Test
    @DisplayName("should get all students with pagination")
    void shouldGetAllStudents() {
        // Arrange
        Student student1 = Student.builder()
                .studentId("SV001")
                .fullName("John Doe")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .build();

        Student student2 = Student.builder()
                .studentId("SV002")
                .fullName("Jane Doe")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .build();

        List<Student> students = List.of(student1, student2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());

        when(studentRepository.findAll(any(Pageable.class))).thenReturn(studentPage);

        // Act
        Page<StudentResponse> responses = studentService.getAllStudents(pageable);

        // Assert
        assertThat(responses).hasSize(2);
        assertThat(responses.getContent().get(0).getStudentId()).isEqualTo("SV001");
        assertThat(responses.getContent().get(0).getFullName()).isEqualTo("John Doe");
        assertThat(responses.getContent().get(1).getStudentId()).isEqualTo("SV002");
        assertThat(responses.getContent().get(1).getFullName()).isEqualTo("Jane Doe");
    }

    @Test
    @DisplayName("should update student")
    void shouldUpdateStudent() {
        // Arrange
        String studentId = "SV001";
        Student student = Student.builder()
                .studentId(studentId)
                .fullName("John Doe")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .build();

        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .fullName("John Doe Updated")
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StudentResponse response = studentService.updateStudent(studentId, updateRequest);

        // Assert
        assertThat(response.getStudentId()).isEqualTo(studentId);
        assertThat(response.getFullName()).isEqualTo("John Doe Updated");
    }

    @Test
    @DisplayName("should throw exception when student not found for update")
    void shouldThrowExceptionWhenStudentNotFoundForUpdate() {
        // Arrange
        String studentId = "SV001";
        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .fullName("John Doe Updated")
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updateRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Student not found");
    }

    @Test
    @DisplayName("shoud throw exception when faculty not found for update")
    void shouldThrowExceptionWhenFacultyNotFoundForUpdate() {
        // Arrange
        String studentId = "SV001";
        Student student = Student.builder()
                .studentId(studentId)
                .fullName("John Doe")
                .build();

        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .facultyId(1)
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(student));
        when(facultyRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updateRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Faculty not found");
    }

    @Test
    @DisplayName("should throw exception when program not found for update")
    void shouldThrowExceptionWhenProgramNotFoundForUpdate() {
        // Arrange
        String studentId = "SV001";
        Student student = Student.builder()
                .studentId(studentId)
                .fullName("John Doe")
                .build();

        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .programId(2)
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(student));
        when(programRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updateRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Program not found");
    }

    @Test
    @DisplayName("should throw exception when student status not found for update")
    void shouldThrowExceptionWhenStudentStatusNotFoundForUpdate() {
        // Arrange
        String studentId = "SV001";
        Student student = Student.builder()
                .studentId(studentId)
                .fullName("John Doe")
                .build();

        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .studentStatusId(3)
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(student));
        when(studentStatusRepository.findById(3)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updateRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Student status not found");
    }

    @Test
    @DisplayName("should delete student")
    void shouldDeleteStudent() {
        // Arrange
        String studentId = "SV001";
        Student student = Student.builder()
                .studentId(studentId)
                .fullName("John Doe")
                .build();

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(student));

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        assertThat(student.getStudentId()).isEqualTo(studentId);
    }

    @Test
    @DisplayName("should throw exception when student not found for delete")
    void shouldThrowExceptionWhenStudentNotFoundForDelete() {
        // Arrange
        String studentId = "SV001";

        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(studentId);
        });

        assertThat(exception.getMessage()).isEqualTo("Student not found");
    }

    @Test
    @DisplayName("should return students matching the search keyword")
    void shouldSearchStudents() {
        // Arrange
        String keyword = "Doe";
        Pageable pageable = PageRequest.of(0, 3);

        Student student1 = Student.builder()
                .studentId("SV001")
                .fullName("John Doe")
                .email("john.doe@example.com")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .build();

        Student student2 = Student.builder()
                .studentId("SV002")
                .fullName("Jane Doe")
                .email("jane.doe@example.com")
                .faculty(new Faculty())
                .program(new Program())
                .studentStatus(new StudentStatus())
                .build();

        List<Student> students = List.of(student1, student2);
        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());

        when(studentRepository.searchByKeyword(keyword, pageable)).thenReturn(studentPage);

        // Act
        Page<StudentResponse> result = studentService.searchStudent(keyword, pageable);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).getStudentId()).isEqualTo("SV001");
        assertThat(result.getContent().get(1).getStudentId()).isEqualTo("SV002");

        // Verify interaction
        verify(studentRepository, times(1)).searchByKeyword(keyword, pageable);
    }

}
