package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {
    @GetMapping
    public String showLoginPage(){
        return("/login");
    }

    @GetMapping("/home")
    public String showHomePage(){
        return("/index");
    }
}
