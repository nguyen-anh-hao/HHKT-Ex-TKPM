package org.example.backend.mapper;

import org.example.backend.domain.Semester;
import org.example.backend.dto.request.SemesterRequest;
import org.example.backend.dto.response.SemesterResponse;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper {

    public static SemesterResponse mapFromSemesterDomainToSemesterResponse(Semester semester) {
        return SemesterResponse.builder()
                .id(semester.getId())
                .semester(semester.getSemester())
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .academicYear(semester.getAcademicYear())
                .lastCancelDate(semester.getLastCancelDate())
                .createdAt(semester.getCreatedAt())
                .updatedAt(semester.getUpdatedAt())
                .createdBy(semester.getCreatedBy())
                .updatedBy(semester.getUpdatedBy())
                .build();
    }

    public static Semester mapFromSemesterRequestToSemesterDomain(SemesterRequest semesterRequest) {
        return Semester.builder()
                .semester(semesterRequest.getSemester())
                .startDate(semesterRequest.getStartDate())
                .endDate(semesterRequest.getEndDate())
                .academicYear(semesterRequest.getAcademicYear())
                .lastCancelDate(semesterRequest.getLastCancelDate())
                .build();
    }

}
