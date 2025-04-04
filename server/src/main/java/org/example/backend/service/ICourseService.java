package org.example.backend.service;

import jakarta.transaction.Transactional;
import org.example.backend.dto.request.CourseRequest;
import org.example.backend.dto.request.CourseUpdateRequest;
import org.example.backend.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICourseService {

    CourseResponse addCourse(CourseRequest courseRequest);

    CourseResponse getCourseById(Integer courseId);

    Page<CourseResponse> getAllCourses(Pageable pageable);

    CourseResponse updateCourse(Integer courseId, CourseUpdateRequest courseUpdateRequest);

    CourseResponse deleteCourse(Integer courseId);
}
