package com.example.aloevibe.validation.validators;

import com.example.aloevibe.model.dto.UserRegistrationDTO;
import com.example.aloevibe.validation.annotations.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDTO> {

    private String message;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegistrationDTO userRegistrationDTO, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = userRegistrationDTO.getPassword() != null &&
                userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword());

        if (!isValid) {
            constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
