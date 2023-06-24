package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

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
    public String login(@RequestBody @NotNull User user, Model model) {
        if (userService.isValidUser(user.getEmail(), user.getPassword())) {
            model.addAttribute("username", user.getEmail());
            return "redirect:/home"; // Redirect to the home page
        } else {
            model.addAttribute("error", "Invalid username or password. Please try again!");
            return "login"; // Return to the login page with an error message
        }
    }
}
