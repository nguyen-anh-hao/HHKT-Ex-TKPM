package org.example.backend.mapper;

import org.example.backend.domain.Address;
import org.example.backend.domain.Document;
import org.example.backend.domain.Student;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.response.StudentResponse;
import org.springframework.stereotype.Component;

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
}
