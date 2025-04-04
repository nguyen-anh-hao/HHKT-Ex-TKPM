package org.example.backend.service;

import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.LecturerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILecturerService {
    Page<LecturerResponse> getAllLecturers(Pageable pageable);

    LecturerResponse getLecturerById(Integer lecturerId);

    LecturerResponse addLecturer(LecturerRequest request);
}
