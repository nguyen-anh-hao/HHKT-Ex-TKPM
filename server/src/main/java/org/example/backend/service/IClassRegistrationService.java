package org.example.backend.service;

import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClassRegistrationService {
    Page<ClassRegistrationResponse> getAllClassRegistrations(Pageable pageable);

    ClassRegistrationResponse getClassRegistrationById(Integer id);

    ClassRegistrationResponse addClassRegistration(ClassRegistrationRequest classRegistrationRequest);
}
