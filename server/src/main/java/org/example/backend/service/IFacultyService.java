package org.example.backend.service;

import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;

import java.util.List;

public interface IFacultyService {
    FacultyResponse addFaculty(FacultyRequest request);
    List<FacultyResponse> getAllFaculties();
    FacultyResponse getFacultyById(Integer id);
    FacultyResponse updateFaculty(Integer id, FacultyRequest request);
    void deleteFaculty(Integer id);
}
