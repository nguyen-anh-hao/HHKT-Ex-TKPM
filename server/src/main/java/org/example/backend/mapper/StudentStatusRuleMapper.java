package org.example.backend.mapper;

import org.example.backend.domain.StudentStatus;
import org.example.backend.domain.StudentStatusRule;
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.StudentStatusRuleResponse;

public class StudentStatusRuleMapper {

    public static StudentStatusRule mapFromRequestToDomain(StudentStatus currentStatus, StudentStatus allowedTransition) {
        return StudentStatusRule.builder()
                .currentStatus(currentStatus)
                .allowedTransition(allowedTransition)
                .build();
    }

    public static StudentStatusRuleResponse mapFromDomainToStudentStatusRuleResponse(StudentStatusRule studentStatusRule) {
        return StudentStatusRuleResponse.builder()
                .id(studentStatusRule.getId())
                .currentStatusName(studentStatusRule.getCurrentStatus().getStudentStatusName())
                .allowedTransitionName(studentStatusRule.getAllowedTransition().getStudentStatusName())
                .createdAt(studentStatusRule.getCreatedAt())
                .updatedAt(studentStatusRule.getUpdatedAt())
                .createdBy(studentStatusRule.getCreatedBy())
                .updatedBy(studentStatusRule.getUpdatedBy())
                .build();
    }
}
