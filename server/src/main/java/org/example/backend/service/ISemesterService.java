package org.example.backend.service;

import org.example.backend.dto.request.SemesterRequest;
import org.example.backend.dto.response.SemesterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISemesterService {
    Page<SemesterResponse> getAllSemesters(Pageable pageable);

    SemesterResponse getSemesterById(Integer semesterId);

    SemesterResponse addSemester(SemesterRequest semesterRequest);
}
