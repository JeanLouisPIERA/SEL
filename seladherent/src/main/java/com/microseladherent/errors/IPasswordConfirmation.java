package com.microseladherent.errors;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConfirmValidator.class)
@Documented
public @interface IPasswordConfirmation {

	String message() default "La confirmation de votre mot de passe n'est pas correcte";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String password();

    String passwordConfirm();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        IPasswordConfirmation[] value();
    }
	
}
