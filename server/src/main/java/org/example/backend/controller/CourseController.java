package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.CourseRequest;
import org.example.backend.dto.request.CourseUpdateRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.CourseResponse;
import org.example.backend.service.ICourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final ICourseService courseService;

    @PostMapping("")
    public APIResponse addCourse(@RequestBody @Valid CourseRequest courseRequest) {

        log.info("Received request to add course: {}", courseRequest.getCourseName());

        CourseResponse courseResponse = courseService.addCourse(courseRequest);

        log.info("Successfully added course: {}", courseResponse.getCourseName());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(courseResponse)
                .build();
    }

    @GetMapping("")
    public APIResponse getAllCourses(@PageableDefault(size = 3, page = 0) Pageable pageable) {

        log.info("Received request to get all courses");
        Page<CourseResponse> courseResponsePage = courseService.getAllCourses(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(courseResponsePage.getContent())
                .paginationInfo(new PaginationInfo(courseResponsePage))
                .build();
    }

    @GetMapping("/{courseId}")
    public APIResponse getCourseById(@PathVariable Integer courseId) {

        log.info("Received request to get course by id: {}", courseId);
        CourseResponse courseResponse = courseService.getCourseById(courseId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(courseResponse)
                .build();
    }

    @PatchMapping("/{courseId}")
    public APIResponse updateCourse(@PathVariable Integer courseId, @RequestBody @Valid CourseUpdateRequest courseUpdateRequest) {

        log.info("Received request to update course: {}", courseId);

        CourseResponse courseResponse = courseService.updateCourse(courseId, courseUpdateRequest);

        log.info("Successfully updated course: {}", courseResponse.getCourseName());

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(courseResponse)
                .build();
    }

    @DeleteMapping("/{courseId}")
    public APIResponse deleteCourse(@PathVariable Integer courseId) {

        log.info("Received request to delete course: {}", courseId);

        CourseResponse courseResponse = courseService.deleteCourse(courseId);

        log.info("Successfully deleted course: {}", courseId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully deleted course")
                .data(courseResponse)
                .build();
    }
}
