package org.example.backend.service;

import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;

public interface IStudentStatusService {
    StudentStatusResponse addStudentStatus(StudentStatusRequest request);

    String getStudentStatusName(Integer studentStatusId);
}
