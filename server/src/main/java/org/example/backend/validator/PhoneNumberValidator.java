package org.example.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.valueextraction.Unwrapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Object> {

    private static final Map<String, String> PHONE_PATTERNS = Map.ofEntries(
            Map.entry("VN", "^(\\+84|0[3|5|7|8|9])[0-9]{8}$"),  // Vietnam: +84 or 0[3|5|7|8|9]xxxxxxxx
            Map.entry("US", "^(\\+1)?[2-9][0-9]{9}$"),           // USA: +1XXXXXXXXXX or XXXXXXXXXX
            Map.entry("UK", "^(\\+44|0)7[0-9]{9}$"),            // UK: +44 or 07XXXXXXXXX
            Map.entry("FR", "^(\\+33|0)[1-9][0-9]{8}$"),        // France: +33 or 0XXXXXXXXX
            Map.entry("DE", "^(\\+49|0)[1-9][0-9]{9,10}$"),     // Germany: +49 or 0XXXXXXXXX
            Map.entry("IN", "^(\\+91|0)?[6789][0-9]{9}$"),      // India: +91XXXXXXXXXX or 0XXXXXXXXXX
            Map.entry("JP", "^(\\+81|0)[1-9][0-9]{8,9}$"),      // Japan: +81XXXXXXXXX or 0XXXXXXXXX
            Map.entry("CA", "^(\\+1)?[2-9][0-9]{9}$"),         // Canada: +1XXXXXXXXXX or XXXXXXXXXX
            Map.entry("AU", "^(\\+61|0)[2-478][0-9]{8}$"),      // Australia: +61XXXXXXXXX or 0XXXXXXXXX
            Map.entry("BR", "^(\\+55|0)?[1-9][0-9]{9,10}$"),    // Brazil: +55XXXXXXXXXXX
            Map.entry("RU", "^(\\+7|8)?[0-9]{10}$"),           // Russia: +7XXXXXXXXXX or 8XXXXXXXXXX
            Map.entry("IT", "^(\\+39|0)[0-9]{9,10}$"),         // Italy: +39XXXXXXXXXX or 0XXXXXXXXXX
            Map.entry("ES", "^(\\+34|0)[6-9][0-9]{8}$"),       // Spain: +34XXXXXXXXX or 0XXXXXXXXX
            Map.entry("CN", "^(\\+86|0)?1[3-9][0-9]{9}$"),     // China: +86XXXXXXXXXXX
            Map.entry("KR", "^(\\+82|0)[1-9][0-9]{7,8}$"),     // South Korea: +82XXXXXXXXX or 0XXXXXXXXX
            Map.entry("MX", "^(\\+52|0)?[1-9][0-9]{9,10}$"),   // Mexico: +52XXXXXXXXXX
            Map.entry("PH", "^(\\+63|0)[9][0-9]{9}$"),         // Philippines: +63XXXXXXXXX
            Map.entry("ID", "^(\\+62|0)[1-9][0-9]{8,11}$"),    // Indonesia: +62XXXXXXXXXXX
            Map.entry("MY", "^(\\+60|0)[1-9][0-9]{7,8}$")      // Malaysia: +60XXXXXXXXX
    );

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }

        try {
            // Use reflection to get phone and phoneCountry
            String phone = (String) getFieldValue(object, "phone");
            String phoneCountry = (String) getFieldValue(object, "phoneCountry");

            // Skip validation if either phone or phoneCountry is null
            if (phone == null || phoneCountry == null) {
                return true;
            }

            // Validate phone number
            return validatePhoneNumber(phone, phoneCountry, context);

        } catch (Exception e) {
            log.error("Error validating phone number", e);
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        try {
            // Try getter method first
            java.lang.reflect.Method getter = object.getClass().getMethod(
                    "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
            );
            return getter.invoke(object);
        } catch (NoSuchMethodException e) {
            // Fallback to direct field access
            try {
                java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
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

        // Check if country is supported
        if (!PHONE_PATTERNS.containsKey(phoneCountry)) {
            addConstraintViolation(context,
                    "Unsupported country code: " + phoneCountry);
            return false;
        }

        // Get the regex pattern for the specific country
        String regex = PHONE_PATTERNS.get(phoneCountry);

        // Validate phone number
        boolean isValid = Pattern.matches(regex, phone);

        if (!isValid) {
            addConstraintViolation(context,
                    "Invalid phone number format for " + phoneCountry);
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
