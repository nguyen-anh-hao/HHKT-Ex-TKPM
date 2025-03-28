package org.example.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailDomainValidator implements ConstraintValidator<EmailDomain, String> {
    private static final String[] ALLOWED_DOMAINS = {"example.com", "student.university.edu.vn"};

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }
        for (String domain : ALLOWED_DOMAINS) {
            if (email.endsWith(domain)) {
                return true;
            }
        }
        return false;
    }
}
