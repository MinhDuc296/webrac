package com.example.webrac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";   // tương ứng templates/user/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "user/register"; // tương ứng templates/user/register.html
    }
}
