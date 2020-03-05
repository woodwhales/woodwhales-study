package org.woodwhales.validation.demo1.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordEqualValidator.class)
public @interface PasswordEqual {
	
	// 校验未通过时的返回信息
    String message() default "passwords are not equal";

	// 以下两行为固定模板
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}
