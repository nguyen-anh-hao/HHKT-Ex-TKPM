package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.StudentStatus;
import org.example.backend.domain.StudentStatusRule;
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.StudentStatusRuleResponse;
import org.example.backend.mapper.StudentStatusRuleMapper;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.repository.IStudentStatusRuleRepository;
import org.example.backend.service.IStudentStatusRuleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentStatusRuleServiceImpl implements IStudentStatusRuleService {
    private final IStudentStatusRuleRepository studentStatusRuleRepository;
    private final IStudentStatusRepository studentStatusRepository;

    @Override
    public Map<String, Set<String>> getStudentStatusRulesMap() {
        log.info("Fetching student status rules from database");
        Map<String, Set<String>> studentStatusRulesMap = new HashMap<>();

        List<StudentStatusRule> studentStatusRules = studentStatusRuleRepository.findAll();
        for (StudentStatusRule rule: studentStatusRules) {
            String currentStatusName = rule.getCurrentStatus().getStudentStatusName();
            String allowedTransitionStatusName = rule.getAllowedTransition().getStudentStatusName();

            studentStatusRulesMap
                    .computeIfAbsent(normalize(currentStatusName), k -> new HashSet<>())
                    .add(normalize(allowedTransitionStatusName));
        }

        log.info("Successfully fetched student status rules from database");
        return studentStatusRulesMap;
    }

    private String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input.trim(), Normalizer.Form.NFC);
    }

    @Override
    public StudentStatusRuleResponse addStudentStatusRule(StudentStatusRuleRequest request) {
        log.info("Adding student status rule with currentStatusId: {} and allowedTransitionId: {}", request.getCurrentStatusId(), request.getAllowedTransitionId());

        StudentStatus currentStatus;
        StudentStatus allowedTransition;
        try {
            currentStatus = studentStatusRepository.findById(request.getCurrentStatusId()).get();
            allowedTransition = studentStatusRepository.findById(request.getAllowedTransitionId()).get();
        } catch (Exception e) {
            log.error("Error fetching student statuses", e);
            throw new RuntimeException("Error fetching student statuses", e);
        }

        if (currentStatus == null || allowedTransition == null) {
            log.error("Invalid student status IDs provided");
            throw new IllegalArgumentException("Invalid student status IDs provided");
        }

        StudentStatusRule studentStatusRule = StudentStatusRuleMapper.mapFromRequestToDomain(currentStatus, allowedTransition);

        try {
            studentStatusRule = studentStatusRuleRepository.save(studentStatusRule);
        } catch (Exception e) {
            log.error("Error saving student status rule", e);
            throw new RuntimeException("Error saving student status rule", e);
        }

        log.info("Successfully added student status rule with ID: {}", studentStatusRule.getId());

        return StudentStatusRuleMapper.mapFromDomainToStudentStatusRuleResponse(studentStatusRule);
    }

    @Override
    public StudentStatusRuleResponse getStudentStatusRuleById(Integer id) {
        log.info("Fetching student status rule with ID: {}", id);

        StudentStatusRule studentStatusRule;
        try {
            studentStatusRule = studentStatusRuleRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching student status rule", e);
            throw new RuntimeException("Error fetching student status rule", e);
        }

        if (studentStatusRule == null) {
            log.error("Student status rule with ID: {} not found", id);
            throw new IllegalArgumentException("Student status rule with ID: " + id + " not found");
        }

        log.info("Successfully fetched student status rule with ID: {}", id);

        return StudentStatusRuleMapper.mapFromDomainToStudentStatusRuleResponse(studentStatusRule);
    }

    @Override
    public Page<StudentStatusRuleResponse> getAllStudentStatusRules(Pageable pageable) {
        log.info("Fetching all student status rules");

        Page<StudentStatusRule> studentStatusRules;
        try {
            studentStatusRules = studentStatusRuleRepository.findAll(pageable);
        } catch (Exception e) {
            log.error("Error fetching student status rules", e);
            throw new RuntimeException("Error fetching student status rules", e);
        }

        log.info("Successfully fetched all student status rules");

        return studentStatusRules.map(StudentStatusRuleMapper::mapFromDomainToStudentStatusRuleResponse);
    }

    @Override
    public void deleteStudentStatusRule(Integer id) {
        log.info("Deleting student status rule with ID: {}", id);

        try {
            studentStatusRuleRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting student status rule", e);
            throw new RuntimeException("Error deleting student status rule", e);
        }

        log.info("Successfully deleted student status rule with ID: {}", id);
    }

    @Override
    public StudentStatusRuleResponse updateStudentStatusRule(Integer id, StudentStatusRuleRequest request) {
        log.info("Updating student status rule with ID: {}", id);

        StudentStatusRule studentStatusRule;
        try {
            studentStatusRule = studentStatusRuleRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching student status rule", e);
            throw new RuntimeException("Error fetching student status rule", e);
        }

        if (studentStatusRule == null) {
            log.error("Student status rule with ID: {} not found", id);
            throw new IllegalArgumentException("Student status rule with ID: " + id + " not found");
        }

        StudentStatus currentStatus;
        StudentStatus allowedTransition;
        try {
            currentStatus = studentStatusRepository.findById(request.getCurrentStatusId()).get();
            allowedTransition = studentStatusRepository.findById(request.getAllowedTransitionId()).get();
        } catch (Exception e) {
            log.error("Error fetching student statuses", e);
            throw new RuntimeException("Error fetching student statuses", e);
        }

        if (currentStatus == null || allowedTransition == null) {
            log.error("Invalid student status IDs provided");
            throw new IllegalArgumentException("Invalid student status IDs provided");
        }

        studentStatusRule.setCurrentStatus(currentStatus);
        studentStatusRule.setAllowedTransition(allowedTransition);

        try {
            studentStatusRule = studentStatusRuleRepository.save(studentStatusRule);
        } catch (Exception e) {
            log.error("Error updating student status rule", e);
            throw new RuntimeException("Error updating student status rule", e);
        }

        log.info("Successfully updated student status rule with ID: {}", studentStatusRule.getId());

        return StudentStatusRuleMapper.mapFromDomainToStudentStatusRuleResponse(studentStatusRule);
    }

}
