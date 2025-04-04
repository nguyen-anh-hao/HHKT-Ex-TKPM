package org.example.backend.service;

import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.ClassResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClassService {
    Page<ClassResponse> getAllClasses(Pageable pageable);

    ClassResponse getClassById(Integer classId);

    ClassResponse addClass(ClassRequest classRequest);
}
