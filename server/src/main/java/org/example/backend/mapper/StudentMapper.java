package org.example.backend.mapper;

import org.example.backend.domain.Address;
import org.example.backend.domain.Document;
import org.example.backend.domain.Student;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.response.AddressResponse;
import org.example.backend.dto.response.DocumentResponse;
import org.example.backend.dto.response.StudentResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public static Student mapToDomain(StudentRequest studentRequest) {
        Student student = Student.builder()
                .studentId(studentRequest.getStudentId())
                .fullName(studentRequest.getFullName())
                .dob(studentRequest.getDob())
                .gender(studentRequest.getGender())
                .intake(studentRequest.getIntake())
                .email(studentRequest.getEmail())
                .phoneCountry(studentRequest.getPhoneCountry())
                .phone(studentRequest.getPhone())
                .nationality(studentRequest.getNationality())
                .build();

        if (studentRequest.getAddresses() != null) {
            student.setAddresses(studentRequest.getAddresses().stream()
                    .map(addressRequest -> {
                        Address address = AddressMapper.mapToDomain(addressRequest);
                        address.setStudent(student);
                        return address;
                    })
                    .collect(Collectors.toList()));
        }

        if (studentRequest.getDocuments() != null) {
            student.setDocuments(studentRequest.getDocuments().stream()
                    .map(documentRequest -> {
                        Document document = DocumentMapper.mapToDomain(documentRequest);
                        document.setStudent(student);
                        return document;
                    })
                    .collect(Collectors.toList()));
        }

        return student;
    }

    public static StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .fullName(student.getFullName())
                .dob(student.getDob())
                .gender(student.getGender())
                .faculty(student.getFaculty().getFacultyName())
                .intake(student.getIntake())
                .program(student.getProgram().getProgramName())
                .email(student.getEmail())
                .phoneCountry(student.getPhoneCountry())
                .phone(student.getPhone())
                .studentStatus(student.getStudentStatus().getStudentStatusName())
                .nationality(student.getNationality())
                .addresses(student.getAddresses() != null ? student.getAddresses().stream().map(AddressMapper::mapToResponse).collect(Collectors.toList()) : null)
                .documents(student.getDocuments() != null ? student.getDocuments().stream().map(DocumentMapper::mapToResponse).collect(Collectors.toList()) : null)
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .createdBy(student.getCreatedBy())
                .updatedBy(student.getUpdatedBy())
                .build();
    }

    public static StudentResponse mapToResponseWithTranslation(
            Student student,
            Map<String, String> studentTranslations,
            Map<String, String> facultyTranslations,
            Map<String, String> programTranslations,
            Map<String, String> statusTranslations,
            Map<Integer, Map<String, String>> addressTranslations,
            Map<Integer, Map<String, String>> documentTranslations) {

        List<AddressResponse> addressResponses = null;
        if (student.getAddresses() != null && !student.getAddresses().isEmpty()) {
            addressResponses = student.getAddresses().stream()
                    .map(address -> {
                        Map<String, String> translations = addressTranslations.getOrDefault(address.getId(), Collections.emptyMap());
                        return AddressMapper.mapToResponseWithTranslation(address, translations);
                    })
                    .collect(Collectors.toList());
        }

        List<DocumentResponse> documentResponses = null;
        if (student.getDocuments() != null && !student.getDocuments().isEmpty()) {
            documentResponses = student.getDocuments().stream()
                    .map(document -> {
                        Map<String, String> translations = documentTranslations.getOrDefault(document.getId(), Collections.emptyMap());
                        return DocumentMapper.mapToResponseWithTranslation(document, translations);
                    })
                    .collect(Collectors.toList());
        }

        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .fullName(student.getFullName())
                .dob(student.getDob())
                .gender(studentTranslations.getOrDefault("gender", student.getGender()))
                .faculty(facultyTranslations.getOrDefault("facultyName", student.getFaculty().getFacultyName()))
                .intake(student.getIntake())
                .program(programTranslations.getOrDefault("programName", student.getProgram().getProgramName()))
                .email(student.getEmail())
                .phoneCountry(student.getPhoneCountry())
                .phone(student.getPhone())
                .studentStatus(statusTranslations.getOrDefault("studentStatusName", student.getStudentStatus().getStudentStatusName()))
                .nationality(studentTranslations.getOrDefault("nationality", student.getNationality()))
                .addresses(addressResponses)
                .documents(documentResponses)
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .createdBy(student.getCreatedBy())
                .updatedBy(student.getUpdatedBy())
                .build();
    }
}
