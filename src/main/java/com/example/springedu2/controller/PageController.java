package com.example.springedu2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    // 로그인 페이지로 이동
    @GetMapping("/login")
    public  String login() {
        return "login"; // login.html
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // access-denied.html
    }

}
