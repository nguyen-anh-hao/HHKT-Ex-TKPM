package org.example.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.backend.repository.IEmailDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailDomainValidator implements ConstraintValidator<EmailDomain, String> {

    private final IEmailDomainRepository emailDomainRepository;

    @Autowired
    public EmailDomainValidator(IEmailDomainRepository emailDomainRepository) {
        this.emailDomainRepository = emailDomainRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }

        String domain = email.substring(email.indexOf('@') + 1);

        List<org.example.backend.domain.EmailDomain> allowedDomains = emailDomainRepository.findAll();

        for (org.example.backend.domain.EmailDomain allowedDomain : allowedDomains) {
            if (domain.equals(allowedDomain.getDomain())) {
                return true;
            }
        }

        return false;
    }
}