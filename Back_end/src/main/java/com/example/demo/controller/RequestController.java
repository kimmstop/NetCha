package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.crawler.RankingCrawler;
import com.example.demo.dto.UserInfoDto;
import com.example.demo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class RequestController {
    private final UserService userService;

    boolean isReceivedIdDuplicate(String receivedId) {
        if(userService.findIdinDB(receivedId).isEmpty())
            return false;
        else
            return true;
    }

    public String encryptPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(rawPassword);
        return encryptedPassword;
    }

    public void saveReceivedUserInfotoDB(UserInfoDto recieveUserInfo) {
        userService.saveUserInfotoDB(recieveUserInfo);
    }

    @PostMapping("/user")
    public String userSignup(@RequestParam String receivedId, @RequestParam String receivedPassword, @RequestParam String receivedAuth) {
        if(isReceivedIdDuplicate(receivedId)){
            return "redirect:/login";
        }
        else{
            String encryptedPassword = encryptPassword(receivedPassword);
            UserInfoDto receivedUserInfo = new UserInfoDto(receivedId, encryptedPassword, receivedAuth);
            saveReceivedUserInfotoDB(receivedUserInfo);
            return "redirect:/login";
        }
    }
    
    @GetMapping("/ranking")
    public String showRanking() throws IOException {
        RankingCrawler crawler = new RankingCrawler();
        String name = crawler.getNetflixRanking();
        return name;
    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
