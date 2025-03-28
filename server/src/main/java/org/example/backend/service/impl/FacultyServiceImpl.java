package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Faculty;
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.mapper.FacultyMapper;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.service.IFacultyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacultyServiceImpl implements IFacultyService {
    private final IFacultyRepository facultyRepository;

    @Override
    @Transactional
    public FacultyResponse addFaculty(FacultyRequest request) {
        if (facultyRepository.findByFacultyName(request.getFacultyName()).isPresent()) {
            log.error("Faculty already exists");
            throw new RuntimeException("Faculty already exists");
        }

        Faculty faculty = FacultyMapper.mapToDomain(request);
        faculty = facultyRepository.save(faculty);

        log.info("Faculty saved to database successfully");

        return FacultyMapper.mapToResponse(faculty);
    }

    @Override
    public List<FacultyResponse> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();

        log.info("Retrieved all faculties from database");

        return faculties.stream()
                .map(FacultyMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FacultyResponse getFacultyById(Integer id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Faculty not found");
                    return new RuntimeException("Faculty not found");
                });

        log.info("Retrieved faculty from database");

        return FacultyMapper.mapToResponse(faculty);
    }

    @Override
    @Transactional
    public FacultyResponse updateFaculty(Integer id, FacultyRequest request) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Faculty not found");
                    return new RuntimeException("Faculty not found");
                });

        faculty.setFacultyName(request.getFacultyName());

        faculty = facultyRepository.save(faculty);

        log.info("Faculty updated successfully");

        return FacultyMapper.mapToResponse(faculty);
    }

    @Override
    @Transactional
    public void deleteFaculty(Integer id) {
       Faculty faculty = facultyRepository.findById(id)
               .orElseThrow(() -> {
                   log.error("Faculty not found");
                   return new RuntimeException("Faculty not found");
               });

        facultyRepository.delete(faculty);

        log.info("Faculty deleted successfully");
    }
}
