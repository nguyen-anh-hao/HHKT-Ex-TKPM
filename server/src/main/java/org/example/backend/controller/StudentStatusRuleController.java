package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.StudentStatusRuleResponse;
import org.example.backend.service.IStudentStatusRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-status-rules")
@RequiredArgsConstructor
@Slf4j
public class StudentStatusRuleController {
    private final IStudentStatusRuleService studentStatusRuleService;

    @PostMapping("")
    public APIResponse addStudentStatusRule(@RequestBody @Valid StudentStatusRuleRequest request) {
        log.info("Adding student status rule");
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.addStudentStatusRule(request);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse updateStudentStatusRule(@PathVariable Integer id, @Valid @RequestBody StudentStatusRuleRequest request) {
        log.info("Updating student status rule");

        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.updateStudentStatusRule(id, request);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @DeleteMapping("/{id}")
    public APIResponse deleteStudentStatusRule(@PathVariable Integer id) {
        log.info("Deleting student status rule");

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
