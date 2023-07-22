package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        if (userService.isValidUser(user.getEmail(), user.getPassword())) {
            return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid username or password!\"}");        }
    }

    /*



    @GetMapping("/")
    public String getWelcomePage() {
        return "welcome";
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }*/
}
