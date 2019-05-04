package org.woodwhales.king.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woodwhales.king.model.User;

import java.util.Objects;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        Subject subject = SecurityUtils.getSubject();
        if(!Objects.isNull(subject)) {
            subject.logout();
        }
        return login(model);
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin success";
    }

    @GetMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (AuthenticationException exception) {
            model.addAttribute("errorMsg", "用户名或密码错误！");
            return login(model);
        }
        User user = (User)subject.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return index(model);
    }

    @GetMapping("/unauthorized")
    public String unAuthorized() {
        return "unAuthorized";
    }
}
