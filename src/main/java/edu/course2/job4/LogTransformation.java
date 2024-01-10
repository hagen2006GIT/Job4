package edu.course2.job4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE}) @Retention(RetentionPolicy.RUNTIME)
public @interface LogTransformation {
    String value() default "";
}
