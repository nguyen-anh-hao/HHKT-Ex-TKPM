package org.example.backend.service;

import org.example.backend.domain.StudentStatus;
import org.example.backend.domain.StudentStatusRule;
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.StudentStatusRuleResponse;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.repository.IStudentStatusRuleRepository;
import org.example.backend.service.impl.StudentStatusRuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentStatusRuleServiceTest {

    @Mock
    private IStudentStatusRuleRepository studentStatusRuleRepository;

    @Mock
    private IStudentStatusRepository studentStatusRepository;

    @InjectMocks
    private StudentStatusRuleServiceImpl studentStatusRuleService;

    private StudentStatus activeStatus;
    private StudentStatus inactiveStatus;
    private StudentStatus graduatedStatus;
    private StudentStatus suspendedStatus;
    private StudentStatusRule testRule1;
    private StudentStatusRule testRule2;
    private StudentStatusRule testRule3;
    private StudentStatusRuleRequest ruleRequest;

    @BeforeEach
    void setUp() {
        setupTestData();
        setupRequests();
    }

    private void setupTestData() {
        activeStatus = StudentStatus.builder()
                .id(1)
                .studentStatusName("Active")
                .students(Collections.emptyList())
                .currentStatusRules(Collections.emptyList())
                .allowedTransitionRules(Collections.emptyList())
                .build();

        inactiveStatus = StudentStatus.builder()
                .id(2)
                .studentStatusName("Inactive")
                .students(Collections.emptyList())
                .currentStatusRules(Collections.emptyList())
                .allowedTransitionRules(Collections.emptyList())
                .build();

        graduatedStatus = StudentStatus.builder()
                .id(3)
                .studentStatusName("Graduated")
                .students(Collections.emptyList())
                .currentStatusRules(Collections.emptyList())
                .allowedTransitionRules(Collections.emptyList())
                .build();

        suspendedStatus = StudentStatus.builder()
                .id(4)
                .studentStatusName("Suspended")
                .students(Collections.emptyList())
                .currentStatusRules(Collections.emptyList())
                .allowedTransitionRules(Collections.emptyList())
                .build();

        testRule1 = StudentStatusRule.builder()
                .id(1)
                .currentStatus(activeStatus)
                .allowedTransition(inactiveStatus)
                .build();
        testRule1.setCreatedAt(LocalDateTime.now());
        testRule1.setUpdatedAt(LocalDateTime.now());
        testRule1.setCreatedBy("admin");
        testRule1.setUpdatedBy("admin");

        testRule2 = StudentStatusRule.builder()
                .id(2)
                .currentStatus(activeStatus)
                .allowedTransition(graduatedStatus)
                .build();
        testRule2.setCreatedAt(LocalDateTime.now());
        testRule2.setUpdatedAt(LocalDateTime.now());
        testRule2.setCreatedBy("admin");
        testRule2.setUpdatedBy("admin");

        testRule3 = StudentStatusRule.builder()
                .id(3)
                .currentStatus(inactiveStatus)
                .allowedTransition(activeStatus)
                .build();
        testRule3.setCreatedAt(LocalDateTime.now());
        testRule3.setUpdatedAt(LocalDateTime.now());
        testRule3.setCreatedBy("admin");
        testRule3.setUpdatedBy("admin");
    }

    private void setupRequests() {
        ruleRequest = new StudentStatusRuleRequest();
        ruleRequest.setCurrentStatusId(1);
        ruleRequest.setAllowedTransitionId(2);
    }

    @Test
    void getStudentStatusRulesMap_WithValidRules_ShouldReturnMappedRules() {
        // Given
        List<StudentStatusRule> rules = Arrays.asList(testRule1, testRule2, testRule3);
        when(studentStatusRuleRepository.findAll()).thenReturn(rules);

        // When
        Map<String, Set<String>> result = studentStatusRuleService.getStudentStatusRulesMap();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.containsKey("Active"));
        Set<String> activeTransitions = result.get("Active");
        assertEquals(2, activeTransitions.size());
        assertTrue(activeTransitions.contains("Inactive"));
        assertTrue(activeTransitions.contains("Graduated"));

        assertTrue(result.containsKey("Inactive"));
        Set<String> inactiveTransitions = result.get("Inactive");
        assertEquals(1, inactiveTransitions.size());
        assertTrue(inactiveTransitions.contains("Active"));

        verify(studentStatusRuleRepository).findAll();
    }

    @Test
    void getStudentStatusRulesMap_WithEmptyRules_ShouldReturnEmptyMap() {
        // Given
        when(studentStatusRuleRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        Map<String, Set<String>> result = studentStatusRuleService.getStudentStatusRulesMap();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(studentStatusRuleRepository).findAll();
    }

    @Test
    void getStudentStatusRulesMap_WithSingleRule_ShouldReturnSingleMapping() {
        // Given
        List<StudentStatusRule> singleRule = Arrays.asList(testRule1);
        when(studentStatusRuleRepository.findAll()).thenReturn(singleRule);

        // When
        Map<String, Set<String>> result = studentStatusRuleService.getStudentStatusRulesMap();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        assertTrue(result.containsKey("Active"));
        Set<String> activeTransitions = result.get("Active");
        assertEquals(1, activeTransitions.size());
        assertTrue(activeTransitions.contains("Inactive"));

        verify(studentStatusRuleRepository).findAll();
    }

    @Test
    void addStudentStatusRule_WithValidRequest_ShouldCreateRule() {
        // Given
        when(studentStatusRepository.findById(ruleRequest.getCurrentStatusId()))
                .thenReturn(Optional.of(activeStatus));
        when(studentStatusRepository.findById(ruleRequest.getAllowedTransitionId()))
                .thenReturn(Optional.of(inactiveStatus));
        when(studentStatusRuleRepository.save(any(StudentStatusRule.class)))
                .thenReturn(testRule1);

        // When
        StudentStatusRuleResponse result = studentStatusRuleService.addStudentStatusRule(ruleRequest);

        // Then
        assertNotNull(result);
        assertEquals(testRule1.getId(), result.getId());
        assertEquals(testRule1.getCurrentStatus().getStudentStatusName(), result.getCurrentStatusName());
        assertEquals(testRule1.getAllowedTransition().getStudentStatusName(), result.getAllowedTransitionName());
        assertEquals(testRule1.getCreatedAt(), result.getCreatedAt());
        assertEquals(testRule1.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(testRule1.getCreatedBy(), result.getCreatedBy());
        assertEquals(testRule1.getUpdatedBy(), result.getUpdatedBy());

        verify(studentStatusRepository).findById(ruleRequest.getCurrentStatusId());
        verify(studentStatusRepository).findById(ruleRequest.getAllowedTransitionId());
        verify(studentStatusRuleRepository).save(any(StudentStatusRule.class));
    }

    @Test
    void addStudentStatusRule_WithDifferentStatuses_ShouldCreateCorrectRule() {
        // Given
        StudentStatusRuleRequest graduationRequest = new StudentStatusRuleRequest();
        graduationRequest.setCurrentStatusId(1); // Active
        graduationRequest.setAllowedTransitionId(3); // Graduated

        when(studentStatusRepository.findById(graduationRequest.getCurrentStatusId()))
                .thenReturn(Optional.of(activeStatus));
        when(studentStatusRepository.findById(graduationRequest.getAllowedTransitionId()))
                .thenReturn(Optional.of(graduatedStatus));
        when(studentStatusRuleRepository.save(any(StudentStatusRule.class)))
                .thenReturn(testRule2);

        // When
        StudentStatusRuleResponse result = studentStatusRuleService.addStudentStatusRule(graduationRequest);

        // Then
        assertNotNull(result);
        assertEquals(testRule2.getId(), result.getId());
        assertEquals("Active", result.getCurrentStatusName());
        assertEquals("Graduated", result.getAllowedTransitionName());

        verify(studentStatusRepository).findById(graduationRequest.getCurrentStatusId());
        verify(studentStatusRepository).findById(graduationRequest.getAllowedTransitionId());
        verify(studentStatusRuleRepository).save(any(StudentStatusRule.class));
    }

    @Test
    void getStudentStatusRuleById_WithValidId_ShouldReturnRule() {
        // Given
        Integer ruleId = 1;
        when(studentStatusRuleRepository.findById(ruleId)).thenReturn(Optional.of(testRule1));

        // When
        StudentStatusRuleResponse result = studentStatusRuleService.getStudentStatusRuleById(ruleId);

        // Then
        assertNotNull(result);
        assertEquals(testRule1.getId(), result.getId());
        assertEquals(testRule1.getCurrentStatus().getStudentStatusName(), result.getCurrentStatusName());
        assertEquals(testRule1.getAllowedTransition().getStudentStatusName(), result.getAllowedTransitionName());
        assertEquals(testRule1.getCreatedAt(), result.getCreatedAt());
        assertEquals(testRule1.getUpdatedAt(), result.getUpdatedAt());

        verify(studentStatusRuleRepository).findById(ruleId);
    }

    @Test
    void getAllStudentStatusRules_WithValidPageable_ShouldReturnPageOfRules() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<StudentStatusRule> rulePage = new PageImpl<>(Arrays.asList(testRule1, testRule2, testRule3), pageable, 3);
        when(studentStatusRuleRepository.findAll(pageable)).thenReturn(rulePage);

        // When
        Page<StudentStatusRuleResponse> result = studentStatusRuleService.getAllStudentStatusRules(pageable);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(3, result.getContent().size());

        StudentStatusRuleResponse firstRule = result.getContent().get(0);
        assertEquals(testRule1.getId(), firstRule.getId());
        assertEquals("Active", firstRule.getCurrentStatusName());
        assertEquals("Inactive", firstRule.getAllowedTransitionName());

        StudentStatusRuleResponse secondRule = result.getContent().get(1);
        assertEquals(testRule2.getId(), secondRule.getId());
        assertEquals("Active", secondRule.getCurrentStatusName());
        assertEquals("Graduated", secondRule.getAllowedTransitionName());

        StudentStatusRuleResponse thirdRule = result.getContent().get(2);
        assertEquals(testRule3.getId(), thirdRule.getId());
        assertEquals("Inactive", thirdRule.getCurrentStatusName());
        assertEquals("Active", thirdRule.getAllowedTransitionName());

        verify(studentStatusRuleRepository).findAll(pageable);
    }

    @Test
    void getAllStudentStatusRules_WithEmptyResult_ShouldReturnEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<StudentStatusRule> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(studentStatusRuleRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<StudentStatusRuleResponse> result = studentStatusRuleService.getAllStudentStatusRules(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(studentStatusRuleRepository).findAll(pageable);
    }

    @Test
    void deleteStudentStatusRule_WithValidId_ShouldDeleteRule() {
        // Given
        Integer ruleId = 1;

        // When
        studentStatusRuleService.deleteStudentStatusRule(ruleId);

        // Then
        verify(studentStatusRuleRepository).deleteById(ruleId);
    }

    @Test
    void updateStudentStatusRule_WithValidRequest_ShouldUpdateRule() {
        // Given
        Integer ruleId = 1;
        StudentStatusRuleRequest updateRequest = new StudentStatusRuleRequest();
        updateRequest.setCurrentStatusId(2); // Inactive
        updateRequest.setAllowedTransitionId(4); // Suspended

        StudentStatusRule updatedRule = StudentStatusRule.builder()
                .id(ruleId)
                .currentStatus(inactiveStatus)
                .allowedTransition(suspendedStatus)
                .build();
        updatedRule.setCreatedAt(LocalDateTime.now());
        updatedRule.setUpdatedAt(LocalDateTime.now());
        updatedRule.setCreatedBy("admin");

        updatedRule.setUpdatedBy("admin");

        when(studentStatusRepository.findById(updateRequest.getCurrentStatusId()))
                .thenReturn(Optional.of(inactiveStatus));
        when(studentStatusRepository.findById(updateRequest.getAllowedTransitionId()))
                .thenReturn(Optional.of(suspendedStatus));
        when(studentStatusRuleRepository.findById(ruleId)).thenReturn(Optional.of(testRule1));
        when(studentStatusRuleRepository.save(any(StudentStatusRule.class)))
                .thenReturn(updatedRule);

        // When
        StudentStatusRuleResponse result = studentStatusRuleService.updateStudentStatusRule(ruleId, updateRequest);
        // Then
        assertNotNull(result);
        assertEquals(updatedRule.getId(), result.getId());
        assertEquals(updatedRule.getCurrentStatus().getStudentStatusName(), result.getCurrentStatusName());
        assertEquals(updatedRule.getAllowedTransition().getStudentStatusName(), result.getAllowedTransitionName());
        assertEquals(updatedRule.getCreatedAt(), result.getCreatedAt());
        assertEquals(updatedRule.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(updatedRule.getCreatedBy(), result.getCreatedBy());
        assertEquals(updatedRule.getUpdatedBy(), result.getUpdatedBy());
        verify(studentStatusRepository).findById(updateRequest.getCurrentStatusId());
        verify(studentStatusRepository).findById(updateRequest.getAllowedTransitionId());
        verify(studentStatusRuleRepository).findById(ruleId);
        verify(studentStatusRuleRepository).save(any(StudentStatusRule.class));
    }
}