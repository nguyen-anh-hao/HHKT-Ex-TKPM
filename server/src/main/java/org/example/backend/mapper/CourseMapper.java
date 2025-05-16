package org.example.backend.mapper;

import org.example.backend.domain.Course;
import org.example.backend.dto.request.CourseRequest;
import org.example.backend.dto.response.CourseResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CourseMapper {

    public static CourseResponse mapFromCourseDomainToCourseResponse(Course course) {
        return CourseResponse.builder()
                .courseId(course.getId())
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .credits(course.getCredits())
                .description(course.getDescription())
                .isActive(course.getIsActive())
                .facultyId(course.getFaculty().getId())
                .facultyName(course.getFaculty().getFacultyName())
                .prerequisiteCourseId(course.getPrerequisiteCourse() != null ? course.getPrerequisiteCourse().getId() : null)
                .prerequisiteCourseName(course.getPrerequisiteCourse() != null ? course.getPrerequisiteCourse().getCourseName() : null)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .createdBy(course.getCreatedBy())
                .updatedBy(course.getUpdatedBy())
                .build();
    }

    public static CourseResponse mapFromCourseDomainToCourseResponseWithTranslation(
            Course course,
            Map<String, String> courseTranslations,
            Map<String, String> facultyTranslations,
            Map<String, String> prerequisiteCourseTranslations) {

        return CourseResponse.builder()
                .courseId(course.getId())
                .courseCode(course.getCourseCode())
                .courseName(courseTranslations.getOrDefault("courseName", course.getCourseName()))
                .credits(course.getCredits())
                .description(courseTranslations.getOrDefault("description", course.getDescription()))
                .isActive(course.getIsActive())
                .facultyId(course.getFaculty().getId())
                .facultyName(facultyTranslations.getOrDefault("facultyName", course.getFaculty().getFacultyName()))
                .prerequisiteCourseId(course.getPrerequisiteCourse() != null ? course.getPrerequisiteCourse().getId() : null)
                .prerequisiteCourseName(course.getPrerequisiteCourse() != null ?
                        prerequisiteCourseTranslations.getOrDefault("courseName", course.getPrerequisiteCourse().getCourseName()) :
                        null)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .createdBy(course.getCreatedBy())
                .updatedBy(course.getUpdatedBy())
                .build();
    }

    public static Course mapFromCourseRequestToCourseDomain(CourseRequest courseRequest) {
        return Course.builder()
                .courseCode(courseRequest.getCourseCode())
                .courseName(courseRequest.getCourseName())
                .credits(courseRequest.getCredits())
                .description(courseRequest.getDescription())
                .isActive(courseRequest.getIsActive())
                .build();
    }
}
