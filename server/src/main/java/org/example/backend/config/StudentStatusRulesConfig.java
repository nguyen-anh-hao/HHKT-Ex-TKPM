package org.example.backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class StudentStatusRulesConfig {
    private final Map<String, Set<String>> studentStatusRulesMap;

    public StudentStatusRulesConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/student-status-rules.json");
        if (inputStream == null) {
            log.error("student-status-rules.json not found");
            throw new RuntimeException("student-status-rules.json not found");
        }
        log.info("Loading student status rules from student-status-rules.json");
        studentStatusRulesMap = objectMapper.readValue(inputStream, new TypeReference<>() {});
        log.info("Successfully loaded student status rules");
    }

    public boolean isValidTransition(String currentStatus, String newStatus) {
        log.info("Checking if transition from {} to {} is valid", currentStatus, newStatus);
        boolean isValid = studentStatusRulesMap.getOrDefault(currentStatus, Set.of()).contains(newStatus);
        log.info("Transition from {} to {} is {}", currentStatus, newStatus, isValid ? "valid" : "invalid");
        return isValid;
    }
}
