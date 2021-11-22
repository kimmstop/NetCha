package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.dto.UserInfoDto;
import com.example.demo.service.UserService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        UserInfoDto newUserInfo = new UserInfoDto();
        newUserInfo.setId(user_id);
        newUserInfo.setPassword(password);
        newUserInfo.setAuth(auth);
        userService.saveUserInfotoDB(newUserInfo);
        userService.findAllUserinDB();
        userService.findAll();
        //userService.getAllUsers();
        return "redirect:/login";
    }
    @GetMapping("/find")
    public void find(){
        ArrayList<HashMap<String, Object>> user_list = userService.findAll();
        for(int i = 0; i < user_list.size(); i++){
            System.out.println(user_list.get(i));
        }
    }
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
