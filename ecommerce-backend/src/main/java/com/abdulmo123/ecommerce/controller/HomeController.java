package com.abdulmo123.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/home", produces="application/json")
    public String getHomePage() {
        return "/home";
    }
}
