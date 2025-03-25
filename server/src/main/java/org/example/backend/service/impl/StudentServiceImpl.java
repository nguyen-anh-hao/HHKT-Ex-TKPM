package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Program;
import org.example.backend.domain.Address;
import org.example.backend.domain.Document;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.mapper.AddressMapper;
import org.example.backend.mapper.DocumentMapper;
import org.example.backend.mapper.StudentMapper;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;
    private final IProgramRepository programRepository;
    private final IFacultyRepository facultyRepository;
    private final IStudentStatusRepository studentStatusRepository;

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
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        log.info("Student {} has been retrieved", student.getStudentId());

        return StudentMapper.mapToResponse(student);
    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(StudentMapper::mapToResponse);
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(String studentId, StudentRequest request) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Faculty faculty = facultyRepository.findById(request.getFacultyId()).orElseThrow(() -> new RuntimeException("Faculty not found"));
        Program program = programRepository.findById(request.getProgramId()).orElseThrow(() -> new RuntimeException("Program not found"));
        StudentStatus studentStatus = studentStatusRepository.findById(request.getStudentStatusId()).orElseThrow(() -> new RuntimeException("Student status not found"));

        student.setFullName(request.getFullName());
        student.setDob(request.getDob());
        student.setGender(request.getGender());
        student.setIntake(request.getIntake());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setNationality(request.getNationality());
        student.setFaculty(faculty);
        student.setProgram(program);
        student.setStudentStatus(studentStatus);

        log.info("Student {} has been updated, all fields except addresses and documents", student.getStudentId());

        // Update Addresses (Remove only addresses that are no longer in request)
        List<Address> existingAddresses = student.getAddresses();
        List<Address> updatedAddresses = request.getAddresses().stream()
                .map(addressRequest -> {
                    Address address = AddressMapper.mapToDomain(addressRequest);
                    address.setStudent(student);
                    return address;
                })
                .toList();

        existingAddresses.clear();
        existingAddresses.addAll(updatedAddresses);

        log.info("Addresses of Student {} have been updated", student.getStudentId());

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

        log.info("Documents of Student {} have been updated", student.getStudentId());

        Student updatedStudent = studentRepository.save(student);

        log.info("Student {} has been updated with all fields and saved", student.getStudentId());

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
        return studentRepository.searchByKeyword(keyword, pageable).map(StudentMapper::mapToResponse);
    }
}
