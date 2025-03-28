package org.example.backend.service;

import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;

import java.util.List;

public interface IStudentStatusService {
    StudentStatusResponse addStudentStatus(StudentStatusRequest request);
    List<StudentStatusResponse> getAllStudentStatuses();
    StudentStatusResponse getStudentStatusById(Integer studentStatusId);
    StudentStatusResponse updateStudentStatus(Integer studentStatusId, StudentStatusRequest request);
    void deleteStudentStatus(Integer studentStatusId);
}
