package org.example.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.backend.config.StudentStatusRulesConfig;
import org.example.backend.dto.request.StudentRequest;

import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.service.IStudentService;
import org.example.backend.service.IStudentStatusService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentStatusTransitionValidator implements ConstraintValidator<ValidStudentStatusTransition, StudentUpdateRequest> {
    private final StudentStatusRulesConfig studentStatusRulesConfig;
    private final IStudentService studentService;
    private final IStudentStatusService studentStatusService;

    @Override
    public boolean isValid(StudentUpdateRequest request, ConstraintValidatorContext context) {

        if (request.getStudentStatusId() == null) {
            return true;
        }

        String currentStatus = studentService.getStudent(request.getStudentId()).getStudentStatus();
        String newStatus = studentStatusService.getStudentStatusName(request.getStudentStatusId());

        // if the student status is not changing, then the transition is valid
        if (currentStatus.equals(newStatus)) {
            return true;
        }

        boolean isValid = studentStatusRulesConfig.isValidTransition(currentStatus, newStatus);
        if (!isValid) {
            // Disable the default message and add a custom one
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Transition from " + currentStatus + " to " + newStatus + " is not allowed.")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
