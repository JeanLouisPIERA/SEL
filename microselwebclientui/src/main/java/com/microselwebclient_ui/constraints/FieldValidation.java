package com.microselwebclient_ui.constraints;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Target({
    TYPE,
    ANNOTATION_TYPE
})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldValidator.class)
@Documented
public @interface FieldValidation {
    String message() default "{constraints.field-match}";
    Class < ? > [] groups() default {};
    Class < ? extends Payload > [] payload() default {};
    String first();
    String second();

    @Target({
        TYPE,
        ANNOTATION_TYPE
    })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldValidation[] value();
    }
}
