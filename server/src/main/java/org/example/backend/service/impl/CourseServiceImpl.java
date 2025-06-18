package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.RegistrationStatus;
import org.example.backend.domain.Class;
import org.example.backend.domain.Course;
import org.example.backend.domain.Faculty;
import org.example.backend.dto.request.CourseRequest;
import org.example.backend.dto.request.CourseUpdateRequest;
import org.example.backend.dto.response.CourseResponse;
import org.example.backend.mapper.CourseMapper;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.repository.IClassRepository;
import org.example.backend.repository.ICourseRepository;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.service.ICourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository courseRepository;
    private final IFacultyRepository facultyRepository;
    private final IClassRepository classRepository;
    private final IClassRegistrationRepository classRegistrationRepository;

    @Override
    @Transactional
    public CourseResponse addCourse(CourseRequest courseRequest) {

        // check if course already exists
        log.info("Checking if course already exists: {}", courseRequest.getCourseName());

        if (courseRepository.existsByCourseName(courseRequest.getCourseName())) {
            log.error("Course already exists: {}", courseRequest.getCourseName());
            throw new RuntimeException("Course already exists: " + courseRequest.getCourseName());
        }

        // check if faculty exists
        log.info("Checking if faculty exists: {}", courseRequest.getFacultyId());
        Faculty faculty = facultyRepository.findById(courseRequest.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        // check if prerequisite course exists
        if (courseRequest.getPrerequisiteCourseId() != null) {
            log.info("Checking if prerequisite course exists: {}", courseRequest.getPrerequisiteCourseId());
            Course prerequisiteCourse = courseRepository.findById(courseRequest.getPrerequisiteCourseId())
                    .orElseThrow(() -> new RuntimeException("Prerequisite course not found"));
        }

        // add course
        log.info("Adding course: {}", courseRequest.getCourseName());
        Course course = CourseMapper.mapFromCourseRequestToCourseDomain(courseRequest);
        course.setFaculty(faculty);

        if (courseRequest.getPrerequisiteCourseId() != null) {
            Course prerequisiteCourse = courseRepository.findById(courseRequest.getPrerequisiteCourseId())
                    .orElseThrow(() -> new RuntimeException("Prerequisite course not found"));
            course.setPrerequisiteCourse(prerequisiteCourse);
        }

        courseRepository.save(course);

        log.info("Course added successfully: {}", courseRequest.getCourseName());

        return CourseMapper.mapFromCourseDomainToCourseResponse(course);
    }

    @Override
    public CourseResponse getCourseById(Integer courseId) {
        log.info("Getting course by id: {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return CourseMapper.mapFromCourseDomainToCourseResponse(course);
    }

    @Override
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        log.info("Getting all courses");
        Page<Course> coursePage = courseRepository.findAll(pageable);

        return coursePage.map(CourseMapper::mapFromCourseDomainToCourseResponse);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Integer courseId, CourseUpdateRequest courseUpdateRequest) {
        log.info("Updating course: {}", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // ❌ Cannot change credits if students are already registered
        if (courseUpdateRequest.getCredits() != null) {
            log.info("Getting all classes for course: {}", courseId);
            Optional<List<Class>> classes = classRepository.findAllByCourseId(courseId).or(() -> Optional.of(List.of()));

            log.info("Checking if students are already registered classes of the course: {}", courseId);
            if (!classes.get().isEmpty()) {

                log.info("Checking if students are already registered in active classes of the course: {}", courseId);
                boolean studentsRegistered = classRegistrationRepository.existsActiveRegistrationsByClassIds(
                        classes.get().stream().map(Class::getId).toList(), RegistrationStatus.REGISTERED
                );

                if (studentsRegistered) {
                    log.error("Cannot change credits if students are already registered");
                    throw new RuntimeException("Cannot change credits if students are already registered");
                }
            }

            course.setCredits(courseUpdateRequest.getCredits());
        }

        if (courseUpdateRequest.getFacultyId() != null) {
            // check if faculty exists
            log.info("Checking if faculty exists: {}", courseUpdateRequest.getFacultyId());
            Faculty faculty = facultyRepository.findById(courseUpdateRequest.getFacultyId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));

            course.setFaculty(faculty);
        }

        if (courseUpdateRequest.getCourseName() != null) {
            course.setCourseName(courseUpdateRequest.getCourseName());
        }

        if (courseUpdateRequest.getDescription() != null) {
            course.setDescription(courseUpdateRequest.getDescription());
        }

        courseRepository.save(course);
        log.info("Course updated successfully: {}", course.getCourseName());

        return CourseMapper.mapFromCourseDomainToCourseResponse(course);
    }

    @Transactional
    @Override
    public CourseResponse deleteCourse(Integer courseId) {
        log.info("Deleting course: {}", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Check if course was created within the last 30 minutes
        log.info("Checking if course was created within the last 30 minutes: {}", courseId);
        if (Duration.between(course.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(), Instant.now()).toMinutes() > 30) {
            log.error("Cannot delete course {} - it was created more than 30 minutes ago.", courseId);
            throw new RuntimeException("Cannot delete course - it was created more than 30 minutes ago.");
        }

        // Check if classes exist for this course
        boolean hasClasses = classRepository.existsByCourseId(courseId);

        if (hasClasses) {
            // Deactivate instead of deleting
            log.info("Cannot delete course {} - it has classes, marking as inactive.", courseId);
            course.setIsActive(false);
            courseRepository.save(course);
            return CourseMapper.mapFromCourseDomainToCourseResponse(course);
        }

        // If no classes exist, delete course
        log.info("Deleting course {}", courseId);
        courseRepository.delete(course);

        return CourseMapper.mapFromCourseDomainToCourseResponse(course);
    }
}