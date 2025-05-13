package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.LanguageInterceptor;
import org.example.backend.domain.Program;
import org.example.backend.domain.Student;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.mapper.ProgramMapper;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.service.IProgramService;
import org.example.backend.service.ITranslationService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramServiceImpl implements IProgramService {
    private final IProgramRepository programRepository;
    private final ITranslationService translationService;

    @Override
    public ProgramResponse addProgram(ProgramRequest request) {
        if (programRepository.findByProgramName(request.getProgramName()).isPresent()) {
            log.error("Program already exists");
            throw new RuntimeException("Program already exists");
        }

        Program program = ProgramMapper.mapToDomain(request);
        program = programRepository.save(program);

        log.info("Program saved to database successfully");

        return ProgramMapper.mapToResponse(program);
    }

    @Override
    public List<ProgramResponse> getAllPrograms() {
        List<Program> programs = programRepository.findAll();
        log.info("Retrieved all programs from database");

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return programs.stream()
                    .map(ProgramMapper::mapToResponse)
                    .collect(Collectors.toList());
        } else {
            // Get translations for programs
            List<Integer> programIds = programs.stream()
                    .map(Program::getId)
                    .toList();
            Map<Integer, Map<String, String>> programTranslations = translationService
                    .getTranslations("Program", programIds, currentLanguage);

            // If programs have students, get translations for students and related entities
            List<Integer> studentIds = programs.stream()
                    .filter(program -> program.getStudents() != null && !program.getStudents().isEmpty())
                    .flatMap(program -> program.getStudents().stream())
                    .map(Student::getStudentId)
                    .map(studentId -> Integer.parseInt(studentId.replaceAll("[^\\d]", "")))
                    .distinct()
                    .toList();

            Map<Integer, Map<String, String>> studentTranslations = Collections.emptyMap();
            if (!studentIds.isEmpty()) {
                studentTranslations = translationService
                        .getTranslations("Student", studentIds, currentLanguage);
            }

            final Map<Integer, Map<String, String>> finalStudentTranslations = studentTranslations;

            return programs.stream()
                    .map(program -> ProgramMapper.mapToResponseWithTranslation(
                            program,
                            programTranslations.getOrDefault(program.getId(), Collections.emptyMap()),
                            finalStudentTranslations
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public ProgramResponse getProgramById(Integer id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Program not found");
                    return new RuntimeException("Program not found");
                });

        log.info("Retrieved program from database");

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return ProgramMapper.mapToResponse(program);
        } else {
            // Get translation for program
            Map<String, String> programTranslations = translationService.getTranslation(
                    "Program", program.getId(), currentLanguage);

            // Get translations for students if present
            Map<Integer, Map<String, String>> studentTranslations = Collections.emptyMap();
            if (program.getStudents() != null && !program.getStudents().isEmpty()) {
                List<Integer> studentIds = program.getStudents().stream()
                        .map(Student::getStudentId)
                        .map(studentId -> Integer.parseInt(studentId.replaceAll("[^\\d]", "")))
                        .toList();
                studentTranslations = translationService
                        .getTranslations("Student", studentIds, currentLanguage);
            }

            return ProgramMapper.mapToResponseWithTranslation(
                    program,
                    programTranslations,
                    studentTranslations
            );
        }
    }

    @Override
    public ProgramResponse updateProgram(Integer id, ProgramRequest request) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Program not found");
                    return new RuntimeException("Program not found");
                });

        program.setProgramName(request.getProgramName());

        program = programRepository.save(program);

        log.info("Program updated successfully");

        return ProgramMapper.mapToResponse(program);
    }

    @Override
    public void deleteProgram(Integer id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Program not found");
                    return new RuntimeException("Program not found");
                });

        programRepository.delete(program);

        log.info("Program deleted successfully");
    }
}
