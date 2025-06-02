package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.LanguageInterceptor;
import org.example.backend.common.RegistrationStatus;
import org.example.backend.domain.Address;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Document;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Program;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.data.TranscriptData;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.mapper.AddressMapper;
import org.example.backend.mapper.DocumentMapper;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentService;
import org.example.backend.service.ITranslationService;
import org.example.backend.service.export.TranscriptPdfExportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;
    private final IProgramRepository programRepository;
    private final IFacultyRepository facultyRepository;
    private final IStudentStatusRepository studentStatusRepository;
    private final IClassRegistrationRepository classRegistrationRepository;
    private final TranscriptPdfExportService transcriptPdfExportService;
    private final ITranslationService translationService;

    @Override
    @Transactional
    public List<StudentResponse> addStudents(List<StudentRequest> requests) {
        return requests.stream()
                .map(this::addStudent)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentResponse addStudent(StudentRequest request) {
        if (studentRepository.findByStudentId(request.getStudentId()).isPresent()) {
            log.error("Student id {} already exists", request.getStudentId());
            throw new RuntimeException("Student id already exists");
        }

        Faculty faculty = facultyRepository.findById(request.getFacultyId()).orElseThrow(() -> new RuntimeException("Faculty not found"));
        Program program = programRepository.findById(request.getProgramId()).orElseThrow(() -> new RuntimeException("Program not found"));
        StudentStatus studentStatus = studentStatusRepository.findById(request.getStudentStatusId()).orElseThrow(() -> new RuntimeException("Student status not found"));

        Student student = StudentMapper.mapToDomain(request);
        student.setFaculty(faculty);
        student.setProgram(program);
        student.setStudentStatus(studentStatus);

        student = studentRepository.save(student);

        log.info("Student {} has been created and saved", student.getStudentId());

        return StudentMapper.mapToResponse(student);
    }

    @Override
    public StudentResponse getStudent(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        log.info("Student {} has been retrieved", student.getStudentId());

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return StudentMapper.mapToResponse(student);
        } else {
            // Get translation for student
            Map<String, String> studentTranslations = translationService.getTranslation(
                    "Student", Integer.parseInt(student.getStudentId().replaceAll("[^\\d]", "")), currentLanguage);

            // Get translation for faculty
            Map<String, String> facultyTranslations = translationService.getTranslation(
                    "Faculty", student.getFaculty().getId(), currentLanguage);

            // Get translation for program
            Map<String, String> programTranslations = translationService.getTranslation(
                    "Program", student.getProgram().getId(), currentLanguage);

            // Get translation for student status
            Map<String, String> studentStatusTranslations = translationService.getTranslation(
                    "StudentStatus", student.getStudentStatus().getId(), currentLanguage);

            // Get translations for addresses if present
            Map<Integer, Map<String, String>> addressTranslations = Collections.emptyMap();
            if (student.getAddresses() != null && !student.getAddresses().isEmpty()) {
                List<Integer> addressIds = student.getAddresses().stream()
                        .map(Address::getId)
                        .toList();
                addressTranslations = translationService
                        .getTranslations("Address", addressIds, currentLanguage);
            }

            // Get translations for documents if present
            Map<Integer, Map<String, String>> documentTranslations = Collections.emptyMap();
            if (student.getDocuments() != null && !student.getDocuments().isEmpty()) {
                List<Integer> documentIds = student.getDocuments().stream()
                        .map(Document::getId)
                        .toList();
                documentTranslations = translationService
                        .getTranslations("Document", documentIds, currentLanguage);
            }

            return StudentMapper.mapToResponseWithTranslation(
                    student,
                    studentTranslations,
                    facultyTranslations,
                    programTranslations,
                    studentStatusTranslations,
                    addressTranslations,
                    documentTranslations
            );
        }
    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return studentPage.map(StudentMapper::mapToResponse);
        } else {
            // Get translations for students
            List<Integer> studentIds = studentPage.getContent().stream()
                    .map(Student::getStudentId)
                    .map(id -> Integer.parseInt(id.replaceAll("[^\\d]", "")))
                    .toList();
            Map<Integer, Map<String, String>> studentTranslations = translationService
                    .getTranslations("Student", studentIds, currentLanguage);

            // Get translations for faculties
            List<Integer> facultyIds = studentPage.getContent().stream()
                    .map(student -> student.getFaculty().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> facultyTranslations = translationService
                    .getTranslations("Faculty", facultyIds, currentLanguage);

            // Get translations for programs
            List<Integer> programIds = studentPage.getContent().stream()
                    .map(student -> student.getProgram().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> programTranslations = translationService
                    .getTranslations("Program", programIds, currentLanguage);

            // Get translations for student statuses
            List<Integer> statusIds = studentPage.getContent().stream()
                    .map(student -> student.getStudentStatus().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> statusTranslations = translationService
                    .getTranslations("StudentStatus", statusIds, currentLanguage);

            // Get all address IDs
            List<Integer> allAddressIds = studentPage.getContent().stream()
                    .filter(student -> student.getAddresses() != null && !student.getAddresses().isEmpty())
                    .flatMap(student -> student.getAddresses().stream())
                    .map(Address::getId)
                    .distinct()
                    .toList();

            // Get translations for addresses
            Map<Integer, Map<String, String>> addressTranslations = allAddressIds.isEmpty() ?
                    Collections.emptyMap() :
                    translationService.getTranslations("Address", allAddressIds, currentLanguage);

            // Get all document IDs
            List<Integer> allDocumentIds = studentPage.getContent().stream()
                    .filter(student -> student.getDocuments() != null && !student.getDocuments().isEmpty())
                    .flatMap(student -> student.getDocuments().stream())
                    .map(Document::getId)
                    .distinct()
                    .toList();

            // Get translations for documents
            Map<Integer, Map<String, String>> documentTranslations = allDocumentIds.isEmpty() ?
                    Collections.emptyMap() :
                    translationService.getTranslations("Document", allDocumentIds, currentLanguage);

            return studentPage.map(student -> StudentMapper.mapToResponseWithTranslation(
                    student,
                    studentTranslations.getOrDefault(Integer.parseInt(student.getStudentId().replaceAll("[^\\d]", "")), Collections.emptyMap()),
                    facultyTranslations.getOrDefault(student.getFaculty().getId(), Collections.emptyMap()),
                    programTranslations.getOrDefault(student.getProgram().getId(), Collections.emptyMap()),
                    statusTranslations.getOrDefault(student.getStudentStatus().getId(), Collections.emptyMap()),
                    addressTranslations,
                    documentTranslations
            ));
        }
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(String studentId, StudentUpdateRequest request) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            log.error("Student not found");
            throw new RuntimeException("Student not found");
        });

        Optional.ofNullable(request.getFullName()).ifPresent(student::setFullName);
        Optional.ofNullable(request.getDob()).ifPresent(student::setDob);
        Optional.ofNullable(request.getGender()).ifPresent(student::setGender);
        Optional.ofNullable(request.getIntake()).ifPresent(student::setIntake);
        Optional.ofNullable(request.getEmail()).ifPresent(student::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(student::setPhone);
        Optional.ofNullable(request.getNationality()).ifPresent(student::setNationality);
        Optional.ofNullable(request.getFacultyId()).ifPresent(facultyId -> {
            Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> {
                log.error("Faculty not found");
                throw new RuntimeException("Faculty not found");
            });
            student.setFaculty(faculty);
        });
        Optional.ofNullable(request.getProgramId()).ifPresent(programId -> {
            Program  program = programRepository.findById(programId).orElseThrow(() -> {
                log.error("Program not found");
                throw new RuntimeException("Program not found");
            });
            student.setProgram(program);
        });
        Optional.ofNullable(request.getStudentStatusId()).ifPresent(studentStatusId -> {
            StudentStatus studentStatus = studentStatusRepository.findById(studentStatusId).orElseThrow(() -> {
                log.error("Student status not found");
                throw new RuntimeException("Student status not found");
            });
            student.setStudentStatus(studentStatus);
        });

        log.info("Student {} has been updated with basic fields", student.getStudentId());

        if (request.getAddresses() != null) {
            student.getAddresses().clear();
            student.getAddresses().addAll(request.getAddresses().stream()
                    .map(addressRequest -> {
                        Address address = AddressMapper.mapToDomain(addressRequest);
                        address.setStudent(student);
                        return address;
                    })
                    .toList());

            log.info("Student {} has been updated with addresses and saved", student.getStudentId());
        }

        if (request.getDocuments() != null) {
            // Update Documents (Update existing or add new)
            List<Document> existingDocuments = student.getDocuments();
            List<Document> updatedDocuments = request.getDocuments().stream()
                    .map(documentRequest -> existingDocuments.stream()
                            .filter(existing -> existing.getDocumentNumber().equals(documentRequest.getDocumentNumber())) // Check if it already exists
                            .findFirst()
                            .map(existing -> { // Update existing
                                existing.setDocumentType(documentRequest.getDocumentType());
                                existing.setIssuedBy(documentRequest.getIssuedBy());
                                existing.setIssuedDate(documentRequest.getIssuedDate());
                                existing.setExpiredDate(documentRequest.getExpiredDate());
                                existing.setIssuedCountry(documentRequest.getIssuedCountry());
                                existing.setNote(documentRequest.getNote());
                                existing.setHasChip(documentRequest.getHasChip());
                                return existing;
                            })
                            .orElseGet(() -> { // Create new if not exists
                                Document document = DocumentMapper.mapToDomain(documentRequest);
                                document.setStudent(student);
                                return document;
                            }))
                    .toList();

            existingDocuments.clear();
            existingDocuments.addAll(updatedDocuments);
            log.info("Student {} has been updated with documents and saved", student.getStudentId());
        }

        Student updatedStudent = studentRepository.save(student);
        log.info("Student {} has been updated and saved", student.getStudentId());

        return StudentMapper.mapToResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepository.delete(student);

        log.info("Student {} has been deleted", student.getStudentId());
    }

    @Override
    public Page<StudentResponse> searchStudent(String keyword, Pageable pageable) {
        Page<Student> studentPage = studentRepository.searchByKeyword(keyword, pageable);

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return studentPage.map(StudentMapper::mapToResponse);
        } else {
            // Get translations for students
            List<Integer> studentIds = studentPage.getContent().stream()
                    .map(Student::getStudentId)
                    .map(id -> Integer.parseInt(id.replaceAll("[^\\d]", "")))
                    .toList();
            Map<Integer, Map<String, String>> studentTranslations = translationService
                    .getTranslations("Student", studentIds, currentLanguage);

            // Get translations for faculties
            List<Integer> facultyIds = studentPage.getContent().stream()
                    .map(student -> student.getFaculty().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> facultyTranslations = translationService
                    .getTranslations("Faculty", facultyIds, currentLanguage);

            // Get translations for programs
            List<Integer> programIds = studentPage.getContent().stream()
                    .map(student -> student.getProgram().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> programTranslations = translationService
                    .getTranslations("Program", programIds, currentLanguage);

            // Get translations for student statuses
            List<Integer> statusIds = studentPage.getContent().stream()
                    .map(student -> student.getStudentStatus().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> statusTranslations = translationService
                    .getTranslations("StudentStatus", statusIds, currentLanguage);

            // Get all address IDs
            List<Integer> allAddressIds = studentPage.getContent().stream()
                    .filter(student -> student.getAddresses() != null && !student.getAddresses().isEmpty())
                    .flatMap(student -> student.getAddresses().stream())
                    .map(Address::getId)
                    .distinct()
                    .toList();

            // Get translations for addresses
            Map<Integer, Map<String, String>> addressTranslations = allAddressIds.isEmpty() ?
                    Collections.emptyMap() :
                    translationService.getTranslations("Address", allAddressIds, currentLanguage);

            // Get all document IDs
            List<Integer> allDocumentIds = studentPage.getContent().stream()
                    .filter(student -> student.getDocuments() != null && !student.getDocuments().isEmpty())
                    .flatMap(student -> student.getDocuments().stream())
                    .map(Document::getId)
                    .distinct()
                    .toList();

            // Get translations for documents
            Map<Integer, Map<String, String>> documentTranslations = allDocumentIds.isEmpty() ?
                    Collections.emptyMap() :
                    translationService.getTranslations("Document", allDocumentIds, currentLanguage);

            return studentPage.map(student -> StudentMapper.mapToResponseWithTranslation(
                    student,
                    studentTranslations.getOrDefault(Integer.parseInt(student.getStudentId().replaceAll("[^\\d]", "")), Collections.emptyMap()),
                    facultyTranslations.getOrDefault(student.getFaculty().getId(), Collections.emptyMap()),
                    programTranslations.getOrDefault(student.getProgram().getId(), Collections.emptyMap()),
                    statusTranslations.getOrDefault(student.getStudentStatus().getId(), Collections.emptyMap()),
                    addressTranslations,
                    documentTranslations
            ));

        }
    }

    @Override
    public byte[] getStudentTranscript(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            log.error("Student not found");
            return new RuntimeException("Student not found");
        });

        List<ClassRegistration> classRegistrations = student.getClassRegistrations();

        List<ClassRegistration> completedRegistrations = classRegistrations.stream()
                .filter(cr -> cr.getStatus() == RegistrationStatus.COMPLETED)
                .toList();

        Double gpa = completedRegistrations.stream()
                .mapToDouble(ClassRegistration::getGrade)
                .average()
                .orElse(0.0);

        TranscriptData transcriptData = TranscriptData.builder()
                .student(student)
                .completedRegistrations(completedRegistrations)
                .gpa(gpa)
                .build();

        try {
            return transcriptPdfExportService.exportData(Collections.singletonList(transcriptData));
        } catch (IOException e) {
            log.error("Error exporting transcript", e);
            throw new RuntimeException("Error exporting transcript", e);
        }
    }
}
