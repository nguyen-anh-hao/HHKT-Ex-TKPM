package org.example.backend.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.example.backend.domain.PhonePattern;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.repository.IPhonePatternRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberValidatorTest {

    private PhoneNumberValidator validator;

    @Mock
    private IPhonePatternRepository phonePatternRepository;

    private static String US_PHONE_PATTERN = "^\\+1[0-9]{10}$";
    private static String VN_PHONE_PATTERN = "^\\+84[0-9]{9}$";

    @BeforeEach
    public void setup() {
        validator = new PhoneNumberValidator(phonePatternRepository);
    }

    private static Stream<Arguments> provideValidPhoneNumbers() {
        return Stream.of(
                Arguments.of("VN", "+84987654321"),
                Arguments.of("VN", "+84123456789"),
                Arguments.of("US", "+14155552671")
        );
    }

    private static Stream<Arguments> provideInvalidPhoneNumbers() {
        return Stream.of(
                Arguments.of("VN", "+85987654321"),
                Arguments.of("VN", "+8498765432"),
                Arguments.of("VN", "+849876543210"),
                Arguments.of("VN", "84987654321"),
                Arguments.of("VN", "+84a87654321"),
                Arguments.of("US", "+44155552671")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidPhoneNumbers")
    public void shouldValidateValidPhoneNumbers(String countryCode, String phoneNumber) {
        PhonePattern phonePattern = new PhonePattern(countryCode,
                countryCode.equals("VN") ? VN_PHONE_PATTERN : US_PHONE_PATTERN, "Country Name");
        when(phonePatternRepository.findById(countryCode)).thenReturn(Optional.of(phonePattern));

        StudentRequest request = new StudentRequest();
        request.setPhone(phoneNumber);
        request.setPhoneCountry(countryCode);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        boolean result = validator.isValid(request, context);

        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhoneNumbers")
    public void shouldValidateInvalidPhoneNumbers(String countryCode, String phoneNumber) {
        PhonePattern phonePattern = new PhonePattern(countryCode,
                countryCode.equals("VN") ? VN_PHONE_PATTERN : US_PHONE_PATTERN, "Country Name");
        when(phonePatternRepository.findById(countryCode)).thenReturn(Optional.of(phonePattern));

        StudentRequest request = new StudentRequest();
        request.setPhone(phoneNumber);
        request.setPhoneCountry(countryCode);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder =
                mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);

        boolean result = validator.isValid(request, context);

        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({
            "'VN', , true, true",
            ", '+84987654321', false, true"
    })
    public void shouldHandleEdgeCases(String countryCode, String phoneNumber, boolean countryExists, boolean expected) {
        if (countryExists) {
            PhonePattern phonePattern = new PhonePattern(countryCode, "^\\+84[0-9]{9}$", "Country Name");
            lenient().when(phonePatternRepository.findById(countryCode)).thenReturn(Optional.of(phonePattern));
        } else {
            lenient().when(phonePatternRepository.findById(anyString())).thenReturn(Optional.empty());
        }

        StudentRequest request = new StudentRequest();
        request.setPhone(phoneNumber);
        request.setPhoneCountry(countryCode);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        boolean result = validator.isValid(request, context);

        assertEquals(expected, result);
    }
}