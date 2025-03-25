package org.example.backend.service;

import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;

public interface IFacultyService {
    FacultyResponse addFaculty(FacultyRequest request);
}
