# 自定义注解校验器

> woodwhales's blog：https://woodwhales.cn/

## demo1-密码及确认密码校验

用户注册的时候需要填写注册密码和确认密码，使用类似 @NotNull 的注解对请求数据进行校验是十分方便的：

自定义`@PasswordEqual`注解，注解在类上

```java
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
```

`@PasswordEqual`注解校验器：

```java
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.woodwhales.validation.demo1.dto.User;

public class PasswordEqualValidator implements ConstraintValidator<PasswordEqual, User> {

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		// 这里只是做示例用，所以简单实用了equals进行对比，实际使用可以根据业务要求做更多拓展
		String password = value.getPassword();
        String confirmPassword = value.getConfirmedPassword();
		return StringUtils.equals(password, confirmPassword);
	}

}
```

使用：

```java
@PostMapping("/demo1/")
public Object demo1(@Validated @RequestBody User user, BindingResult bindingResult) {
    HashMap<String, Object> map = new HashMap<>();
    if(bindingResult.hasErrors()) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError objectError : allErrors) {
            log.debug("errorMessage -> {}", objectError.getDefaultMessage());
        }
        map.put("code", false);
        return map;
    }

    map.put("code", true);
    return map;
}
```

## demo2-数据唯一性校验

用户注册时，需要校验注册账号的唯一性，可以使用`@UniqueLogin`注解，动态校验数据合法性：

```java
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = UniqueLoginValidator.class)
public @interface UniqueLogin {

	String message() default "The given login is already in use";
	
	// 以下两行为固定模板
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
	
}
```

`@UniqueLogin`注解校验器：

```java
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.woodwhales.validation.demo2.repository.UserRepository;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

	private UserRepository userRepository;
	
	// 注意，在Spring4.3之后，构造器自动注入是不需要使用@Autowired注解。
	public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public boolean isValid(String login, ConstraintValidatorContext context) {
		return login != null && !userRepository.findByLogin(login).isPresent();
	}

}
```

查询数据库实现：

```java
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.woodwhales.validation.demo2.dto.User;

@Repository
public class UserRepository {
	
	private List<User> registeredUsers = new LinkedList<>();
	
	public boolean save(User user) {
        registeredUsers.add(user);
        return true;
    }
	
	public Optional<User> findByLogin(String login) {
		save(new User("woodwhales", "admin"));
		
        return registeredUsers.stream()
				                .filter(user -> user.getLogin().equals(login))
				                .findFirst();
    }
	
}
```

使用：

```java
@PostMapping("/demo2/")
public Object demo2(@Validated @RequestBody User user, BindingResult bindingResult) {
    HashMap<String, Object> map = new HashMap<>();
    if(bindingResult.hasErrors()) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError objectError : allErrors) {
            log.debug("errorMessage -> {}", objectError.getDefaultMessage());
        }
        map.put("code", false);
        return map;
    }

    map.put("code", true);
    return map;
}
```

## demo3-数据枚举值合法性校验

数据请求时，使用`@EnumValidator`注解校验数据的是否为合法的枚举值：

```java
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EnumValidatorValidator.class)
public @interface EnumValidator {
    
	Class<?>[] target() default {};
 
    String message() default "入参值不在正确枚举中";
    
    String method() default "getCode";
    
    // 以下两行为固定模板
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
}
```

`@EnumValidator`注解校验器：

```java
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumValidatorValidator implements ConstraintValidator<EnumValidator, Object> {
	
	private List<Object> values = new ArrayList<>();
	
	@Override
	public void initialize(EnumValidator enumValidator) {
		Class<?>[] classArray = enumValidator.target();
		if(classArray.length == 0 ) {
			return;
		}
		
		try {
			addValue(classArray, enumValidator.method());
		} catch (Exception exception) {
			log.error("handle exception process happening exception! {}", exception);
		}
	}
	
	private void addValue(Class<?>[] classArray, String methodString) throws Exception {
		// 获取@EnumValidator注解类上的value配置，即枚举的Class类对象
		for (Class<?> clz : classArray) {
			
			if(clz.isEnum()) {
				// 获取当前枚举类的所有实例对象
				Object[] objects = clz.getEnumConstants();
				// 调用指定的"getCode()"方法，获取Method对象
	            Method method = clz.getMethod(methodString);
	            if (Objects.isNull(method)) {
	                throw new RuntimeException(String.format("枚举对象%s缺少名为%s的方法", clz.getName(), methodString));
	            }
	            Object value;
	            for (Object obj : objects) {
	            	// 依次执行枚举实例的 "getCode()"方法，将其值存入值域列表中
	                value = method.invoke(obj);
	                values.add(value);
	            }
			}
		}
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value instanceof String) {
            String valueStr = (String)value;
            return StringUtils.isEmpty(valueStr) || values.contains(value);
        }
		
        return Objects.isNull(value) || values.contains(value);
	}

}
```

使用：

```java
@PostMapping("/demo3/")
public Object demo3(@Validated @RequestBody User user, BindingResult bindingResult) {
    HashMap<String, Object> map = new HashMap<>();
    if(bindingResult.hasErrors()) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError objectError : allErrors) {
            log.debug("errorMessage -> {}", objectError.getDefaultMessage());
        }
        map.put("code", false);
        return map;
    }

    map.put("code", true);
    return map;
}
```

