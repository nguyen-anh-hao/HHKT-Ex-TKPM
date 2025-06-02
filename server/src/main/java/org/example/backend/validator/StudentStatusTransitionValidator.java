package org.example.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.backend.config.StudentStatusRulesConfig;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentStatusTransitionValidator implements ConstraintValidator<ValidStudentStatusTransition, StudentUpdateRequest> {
    private final StudentStatusRulesConfig studentStatusRulesConfig;
    private final IStudentRepository studentRepository;
    private final IStudentStatusRepository studentStatusRepository;

    @Override
    public boolean isValid(StudentUpdateRequest request, ConstraintValidatorContext context) {

        if (request.getStudentStatusId() == null) {
            return true;
        }

        // Get current student status, or throw exception if not found
        Student student = studentRepository.findByStudentId(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found for ID: " + request.getStudentId()));
        String currentStatus = student.getStudentStatus().getStudentStatusName();

        // Get new status, or throw exception if not found
        StudentStatus newStatus = studentStatusRepository.findById(request.getStudentStatusId())
                .orElseThrow(() -> new IllegalArgumentException("StudentStatus not found for ID: " + request.getStudentStatusId()));
        String newStatusName = newStatus.getStudentStatusName();

        // If the student status is not changing, then the transition is valid
        if (currentStatus.equals(newStatusName)) {
            return true;
        }

        // Validate the status transition
        boolean isValid = studentStatusRulesConfig.isValidTransition(currentStatus, newStatusName);
        if (!isValid) {
            // Disable the default message and add a custom one
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Transition from " + currentStatus + " to " + newStatusName + " is not allowed.")
                    .addConstraintViolation();
        }


        return isValid;
    }
}
