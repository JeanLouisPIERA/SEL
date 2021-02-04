package com.microseladherent.errors;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordConfirmValidator implements ConstraintValidator<IPasswordConfirmation, Object> {

	private String password;
    private String passwordConfirm;
    private String message;

    public void initialize(IPasswordConfirmation constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.passwordConfirm = constraintAnnotation.passwordConfirm();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object beanPassword = new BeanWrapperImpl(value).getPropertyValue(password);
        Object beanPasswordConfirm = new BeanWrapperImpl(value).getPropertyValue(passwordConfirm);

        boolean isValid = false;
        if (beanPassword != null) {
            isValid = beanPassword.equals(beanPasswordConfirm);
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(beanPassword.toString())
                    .addConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(beanPasswordConfirm.toString())
                    .addConstraintViolation();
        }

        return isValid;

    }
	
}
