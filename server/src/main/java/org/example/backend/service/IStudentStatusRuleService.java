package org.example.backend.service;

import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.StudentStatusRuleResponse;

import java.util.Map;
import java.util.Set;

public interface IStudentStatusRuleService {
    Map<String, Set<String>> getStudentStatusRulesMap();

    StudentStatusRuleResponse addStudentStatusRule(StudentStatusRuleRequest request);

    StudentStatusRuleResponse updateStudentStatusRule(Integer id, StudentStatusRuleRequest request);

    void deleteStudentStatusRule(Integer id);

    StudentStatusRuleResponse getStudentStatusRuleById(Integer id);
}
