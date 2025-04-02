package org.example.backend.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.example.backend.domain.EmailDomain;
import org.example.backend.repository.IEmailDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailDomainValidatorTest {

    private EmailDomainValidator validator;

    @Mock
    private IEmailDomainRepository emailDomainRepository;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setup() {
        validator = new EmailDomainValidator(emailDomainRepository);
    }

    @Test
    public void shouldReturnTrueWhenEmailIsNull() {
        assertTrue(validator.isValid(null, context));
    }

    @Test
    public void shouldReturnFalseWhenDomainIsNotAllowed() {
        List<EmailDomain> allowedDomains = Arrays.asList(
                createEmailDomain("gmail.com"),
                createEmailDomain("yahoo.com"),
                createEmailDomain("hotmail.com")
        );

        when(emailDomainRepository.findAll()).thenReturn(allowedDomains);

        assertFalse(validator.isValid("test@outlook.com", context));
        assertFalse(validator.isValid("user@example.com", context));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@gmail.com",
            "user.name@yahoo.com",
            "john.doe+tag@hotmail.com"
    })
    public void shouldValidateCommonEmailFormats(String email) {
        List<EmailDomain> allowedDomains = Arrays.asList(
                createEmailDomain("gmail.com"),
                createEmailDomain("yahoo.com"),
                createEmailDomain("hotmail.com")
        );

        when(emailDomainRepository.findAll()).thenReturn(allowedDomains);

        assertTrue(validator.isValid(email, context));
    }

    @ParameterizedTest
    @CsvSource({
            "test@company.co.uk, company.co.uk, true",
            "user@subdomain.example.com, subdomain.example.com, true",
            "admin@internal.org, example.org, false"
    })
    public void shouldHandleComplexDomains(String email, String allowedDomain, boolean expected) {
        List<EmailDomain> allowedDomains = Collections.singletonList(
                createEmailDomain(allowedDomain)
        );

        when(emailDomainRepository.findAll()).thenReturn(allowedDomains);

        if (expected) {
            assertTrue(validator.isValid(email, context));
        } else {
            assertFalse(validator.isValid(email, context));
        }
    }

    private EmailDomain createEmailDomain(String domain) {
        EmailDomain emailDomain = new EmailDomain();
        emailDomain.setDomain(domain);
        return emailDomain;
    }
}