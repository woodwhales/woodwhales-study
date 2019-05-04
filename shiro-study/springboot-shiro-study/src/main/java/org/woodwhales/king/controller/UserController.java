package org.woodwhales.king.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	/**
	 * 用户添加页面请求
	 * @return
	 */
	@GetMapping("/add")
	public String addUser() {
		return "/user/add";
	}

	/**
	 * 用户更新页面请求
	 * @return
	 */
	@RequiresPermissions("user:update")
	@GetMapping("/update")
	public String updateUser() {
		return "/user/update";
	}
}
