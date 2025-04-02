package org.example.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.PhonePattern;
import org.example.backend.repository.IPhonePatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Object> {

    private final IPhonePatternRepository phonePatternRepository;

    @Autowired
    public PhoneNumberValidator(IPhonePatternRepository phonePatternRepository) {
        this.phonePatternRepository = phonePatternRepository;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }

        try {
            String phone = (String) getFieldValue(object, "phone");
            String phoneCountry = (String) getFieldValue(object, "phoneCountry");

            if (phone == null || phoneCountry == null) {
                return true;
            }

            return validatePhoneNumber(phone, phoneCountry, context);

        } catch (Exception e) {
            log.error("Error validating phone number", e);
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        try {
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getter = object.getClass().getMethod(getterName);
            return getter.invoke(object);
        } catch (NoSuchMethodException e) {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception ex) {
                log.error("Could not access field " + fieldName, ex);
                return null;
            }
        }
    }

    private boolean validatePhoneNumber(
            String phone,
            String phoneCountry,
            ConstraintValidatorContext context) {

        Optional<PhonePattern> patternOpt = phonePatternRepository.findById(phoneCountry);

        if (patternOpt.isEmpty()) {
            addConstraintViolation(context, "Unsupported country code: " + phoneCountry);
            return false;
        }

        String regex = patternOpt.get().getRegexPattern();
        boolean isValid = Pattern.matches(regex, phone);

        if (!isValid) {
            addConstraintViolation(context, "Invalid phone number format for " + phoneCountry);
        }

        return isValid;
    }

    private void addConstraintViolation(
            ConstraintValidatorContext context,
            String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}