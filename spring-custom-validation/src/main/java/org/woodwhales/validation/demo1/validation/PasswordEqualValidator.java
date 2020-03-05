package org.woodwhales.validation.demo1.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.woodwhales.validation.demo1.dto.User;

public class PasswordEqualValidator implements ConstraintValidator<PasswordEqual, User>{

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		// 这里只是做示例用，所以简单实用了equals进行对比，实际使用可以根据业务要求做更多拓展
		String password = value.getPassword();
        String confirmPassword = value.getConfirmedPassword();
		return StringUtils.equals(password, confirmPassword);
	}

}
