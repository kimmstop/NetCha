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
        reg.addViewController("/main").setViewName("main");
        reg.addViewController("/find").setViewName("find");
        reg.addViewController("/login").setViewName("login");
        reg.addViewController("/admin").setViewName("admin");
        reg.addViewController("/signup").setViewName("signup");
        reg.addViewController("/mainpage").setViewName("mainpage");
    }
}
