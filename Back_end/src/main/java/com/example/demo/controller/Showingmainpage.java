package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Showingmainpage {
    @GetMapping("/netcha")
    public String showmainpage(){
        return "모두들 화이팅!";
    }
}
