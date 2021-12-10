package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("/").setViewName("mainpage");
        reg.addViewController("/main").setViewName("mainpage");
        reg.addViewController("/login").setViewName("loginpage");
        reg.addViewController("/admin").setViewName("adminpage");
        reg.addViewController("/signup").setViewName("signuppage");
        reg.addViewController("/user").setViewName("userpage");
        reg.addViewController("/signupfail").setViewName("signupfailpage");
    }
}
