package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.StudentStatusRule;
import org.example.backend.repository.IStudentStatusRuleRepository;
import org.example.backend.service.IStudentStatusRuleService;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentStatusRuleImpl implements IStudentStatusRuleService {
    private final IStudentStatusRuleRepository studentStatusRuleRepository;

    @Override
    public Map<String, Set<String>> getStudentStatusRulesMap() {
        log.info("Fetching student status rules from database");
        Map<String, Set<String>> studentStatusRulesMap = new HashMap<>();

        List<StudentStatusRule> studentStatusRules = studentStatusRuleRepository.findAll();
        for (StudentStatusRule rule: studentStatusRules) {
            studentStatusRulesMap
                    .computeIfAbsent(normalize((rule.getCurrentStatus())), k -> new HashSet<>())
                    .add(normalize(rule.getNewStatus()));
        }

        log.info("Successfully fetched student status rules from database");
        return studentStatusRulesMap;
    }

    private String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input.trim(), Normalizer.Form.NFC);
    }
}
