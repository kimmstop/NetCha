package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.crawler.RankingCrawler;
import com.example.demo.dto.UserInfoDto;
import com.example.demo.service.SignupService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class RequestController {
    private final SignupService signupService;
    @PostMapping("/signup_check")
    public String userSignup(@RequestParam String receivedId, @RequestParam String receivedPassword, @RequestParam String receivedAuth) {
        if(signupService.isReceivedIdDuplicate(receivedId)) {
            return "redirect:/signupfail";
        }
        else {
            UserInfoDto receivedUserInfo = new UserInfoDto(receivedId, receivedPassword, receivedAuth);
            signupService.saveReceivedUserInfoToDB(receivedUserInfo);
            return "redirect:/login";
        }
    }
    /* Mayble.. ToDo....?*/
    @GetMapping("/ranking")
    public String showRanking() throws IOException {
        RankingCrawler crawler = new RankingCrawler();
        String name = crawler.getNetflixRanking();
        return name;
    }
    /*                    */



    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/loginpage";
    }
}
