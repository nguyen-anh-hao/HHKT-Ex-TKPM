package org.example.backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.IStudentStatusRuleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudentStatusRulesConfig {
    private Map<String, Set<String>> studentStatusRulesMap;
    private final IStudentStatusRuleService studentStatusRuleService;

    @PostConstruct
    public void loadStudentRules() {
        studentStatusRulesMap = studentStatusRuleService.getStudentStatusRulesMap();
    }

    private String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input.trim(), Normalizer.Form.NFC);
    }

    public boolean isValidTransition(String currentStatus, String newStatus) {
        log.info("Checking if transition from {} to {} is valid", currentStatus, newStatus);

        String normCurrent = normalize(currentStatus);
        String normNew = normalize(newStatus);

        log.info("Normalized currentStatus [{}]: {}", normCurrent, normCurrent.hashCode());
        log.info("Normalized newStatus [{}]: {}", normNew, normNew.hashCode());

        // Retrieve the transition set safely
        Set<String> possibleTransitions = studentStatusRulesMap.getOrDefault(normCurrent, Set.of());

        log.info("Expected transitions [{}]: {}", possibleTransitions,
                possibleTransitions.stream().map(s -> normalize(s).hashCode()).toList());

        boolean isValid = possibleTransitions.stream()
                .anyMatch(status -> normalize(status).equalsIgnoreCase(normNew));

        log.info("Transition from {} to {} is {}", normCurrent, normNew, isValid ? "valid" : "invalid");
        return isValid;
    }

    @Scheduled(fixedRate = 60000) // Refresh every 60 seconds
    public void refreshRules() {
        log.info("Refreshing student status rules from database...");
        loadStudentRules();
    }

}
