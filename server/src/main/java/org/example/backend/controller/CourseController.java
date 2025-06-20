package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.CourseRequest;
import org.example.backend.dto.request.CourseUpdateRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.CourseResponse;
import org.example.backend.service.ICourseService;
import org.springdoc.core.annotations.ParameterObject;
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
    @Operation(summary = "Add a new course", description = "Create a new course in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
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
    @Operation(summary = "Get all courses", description = "Retrieve all courses with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllCourses(
            @Parameter(description = "Pagination parameters")
            @ParameterObject @PageableDefault(size = 3, page = 0) Pageable pageable) {

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
    @Operation(summary = "Get course by ID", description = "Retrieve course details by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getCourseById(
            @Parameter(description = "ID of the course to retrieve", required = true, example = "1")
            @PathVariable Integer courseId) {

        log.info("Received request to get course by id: {}", courseId);
        CourseResponse courseResponse = courseService.getCourseById(courseId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(courseResponse)
                .build();
    }

    @PatchMapping("/{courseId}")
    @Operation(summary = "Update a course", description = "Update course details by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse updateCourse(
            @Parameter(description = "ID of the course to update", required = true, example = "1")
            @PathVariable Integer courseId,

            @RequestBody @Valid CourseUpdateRequest courseUpdateRequest) {

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
    @Operation(summary = "Delete a course", description = "Delete course by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse deleteCourse(
            @Parameter(description = "ID of the course to delete", required = true, example = "1")
            @PathVariable Integer courseId) {

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
