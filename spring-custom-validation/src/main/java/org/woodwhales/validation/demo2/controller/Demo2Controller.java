package org.woodwhales.validation.demo2.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.validation.demo2.dto.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 参考：https://github.com/danielolszewski/blog/tree/master/spring-custom-validation
 *
 */
@Slf4j
@RestController
public class Demo2Controller {
	
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

}
