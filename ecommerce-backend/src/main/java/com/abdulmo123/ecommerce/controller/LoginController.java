package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@CrossOrigin(origins = {"http://localhost:4200"})
public class LoginController {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
    }

    @GetMapping(value = "/login", produces="application/json")
    public String getLoginPage() {
        return "authentication successful";
    }

}
