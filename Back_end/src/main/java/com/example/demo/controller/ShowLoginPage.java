package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.dto.UserInfoDto;
import com.example.demo.service.UserService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ShowLoginPage {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(@RequestParam String user_id, @RequestParam String password, @RequestParam String auth) {
        UserInfoDto user_info = new UserInfoDto();
        user_info.setId(user_id);
        user_info.setPassword(password);
        user_info.setAuth(auth);
        userService.save(user_info);
        userService.findAll();
        //userService.getAllUsers();
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
