package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.StudentStatusRuleResponse;
import org.example.backend.service.IStudentStatusRuleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student-status-rules")
@RequiredArgsConstructor
@Slf4j
public class StudentStatusRuleController {
    private final IStudentStatusRuleService studentStatusRuleService;

    @GetMapping("/{id}")
    public APIResponse getStudentStatusRuleById(@PathVariable Integer id) {
        log.info("Received request to get student status rule by ID: {}", id);
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.getStudentStatusRuleById(id);

        if (studentStatusRule == null) {
            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Student status rule not found")
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @GetMapping("")
    public APIResponse getAllStudentStatusRules(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all student status rules with pagination: {}", pageable);

        Page<StudentStatusRuleResponse> studentStatusRules = studentStatusRuleService.getAllStudentStatusRules(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRules.getContent())
                .paginationInfo(new PaginationInfo(studentStatusRules))
                .build();
    }

    @PostMapping("")
    public APIResponse addStudentStatusRule(@RequestBody @Valid StudentStatusRuleRequest request) {
        log.info("Adding new student status rule: {}", request);
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.addStudentStatusRule(request);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse updateStudentStatusRule(@PathVariable Integer id, @Valid @RequestBody StudentStatusRuleRequest request) {
        log.info("Updating student status rule with ID: {}, Request: {}", id, request);
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.updateStudentStatusRule(id, request);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @DeleteMapping("/{id}")
    public APIResponse deleteStudentStatusRule(@PathVariable Integer id) {
        log.info("Deleting student status rule with ID: {}", id);

        // Check if the student status rule exists
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.getStudentStatusRuleById(id);
        if (studentStatusRule == null) {
            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Student status rule not found")
                    .build();
        }


        studentStatusRuleService.deleteStudentStatusRule(id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
