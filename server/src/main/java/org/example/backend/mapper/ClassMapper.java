package org.example.backend.mapper;

import org.example.backend.domain.Class;
import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.ClassResponse;
import org.example.backend.dto.response.ClassSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class ClassMapper {
    public static ClassSummaryResponse mapFromDomainToClassSummaryResponse(Class aClass) {
        return ClassSummaryResponse.builder()
                .classCode(aClass.getClassCode())
                .maxStudents(aClass.getMaxStudents())
                .schedule(aClass.getSchedule())
                .room(aClass.getRoom())
                .build();
    }

    public static Class mapFromClassRequestToDomain(ClassRequest classRequest) {
        return Class.builder()
                .classCode(classRequest.getClassCode())
                .maxStudents(classRequest.getMaxStudents())
                .schedule(classRequest.getSchedule())
                .room(classRequest.getRoom())
                .build();
    }

    public static ClassResponse mapFromDomainToClassResponse(Class aClass) {
        return ClassResponse.builder()
                .id(aClass.getId())
                .classCode(aClass.getClassCode())
                .maxStudents(aClass.getMaxStudents())
                .schedule(aClass.getSchedule())
                .room(aClass.getRoom())
                .courseId(aClass.getCourse().getId())
                .courseCode(aClass.getCourse().getCourseCode())
                .courseName(aClass.getCourse().getCourseName())
                .credits(aClass.getCourse().getCredits())
                .facultyName(aClass.getCourse().getFaculty().getFacultyName())
                .prerequisiteCourseCode(aClass.getCourse().getPrerequisiteCourse() != null ? aClass.getCourse().getPrerequisiteCourse().getCourseCode() : null)
                .prerequisiteCourseName(aClass.getCourse().getPrerequisiteCourse() != null ? aClass.getCourse().getPrerequisiteCourse().getCourseName() : null)
                .lecturerId(aClass.getLecturer().getId())
                .lecturerName(aClass.getLecturer().getFullName())
                .semesterId(aClass.getSemester().getId())
                .semesterName(aClass.getSemester().getSemester())
                .createdDate(aClass.getCreatedAt())
                .updatedDate(aClass.getUpdatedAt())
                .createdBy(aClass.getCreatedBy())
                .updatedBy(aClass.getUpdatedBy())
                .build();
    }
}
