package org.woodwhales.validation.demo3.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.validation.demo3.dto.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class Demo3Controller {
	
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
}
