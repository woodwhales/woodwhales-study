package org.woodwhales.king.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	/**
	 * 首页跳转到登录页面的请求
	 * @param model
	 * @return
	 */
	@GetMapping("/toLogin")
	public String toLogin(Model model) {
		return "login";
	}

	/**
	 * 登录页面表单的请求
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param model
	 * @return
	 */
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) boolean rememberMe, Model model) {

		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return toLogin(model.addAttribute("msg", "用户名和密码未填写"));
		}
		
		log.debug("login request， username = {}, rememberMe = {}", username, rememberMe);
		
		// 1 获取当前请求的shiro 主体
		Subject subject = SecurityUtils.getSubject();
		
		// 2 封装请求的用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		// 4 设置记住我功能
		log.debug("rememberMe = [{}]", rememberMe);
		token.setRememberMe(rememberMe);

		// 3 执行登录操作，没有异常抛出表示登录成功
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			log.debug("login failed, cause by : {}", e.getMessage());
			model.addAttribute("msg", "用户名或密码不存在！");
			return toLogin(model);
		}
		return "redirect:/";
	}

	/**
	 * 未授权的所有请求都会跳转到未授权提示页面
	 * @return
	 */
	@GetMapping("/noAuth")
	public String unAuth() {
		return "noAuth";
	}

}
