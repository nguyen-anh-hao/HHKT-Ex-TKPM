package org.example.backend.service;

import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {

    List<StudentResponse> addStudents(List<StudentRequest> requests);

    StudentResponse addStudent(StudentRequest request);

    StudentResponse getStudent(String studentId);

    StudentResponse updateStudent(String studentId, StudentUpdateRequest request);

    Page<StudentResponse> getAllStudents(Pageable pageable);

    void deleteStudent(String studentId);

    Page<StudentResponse> searchStudent(String keyword, Pageable pageable);
}
