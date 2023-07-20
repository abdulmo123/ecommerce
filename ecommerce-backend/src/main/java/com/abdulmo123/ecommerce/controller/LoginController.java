package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = {"http://localhost:4200"})
public class LoginController {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @NotNull User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        if (userService.isValidUser(email, password)) {
            return ResponseEntity.ok("{\"message\": \"Login successful\", \"redirect\": \"/home\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    /*@PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        if (userService.isValidUser(email, password)) {
            return ResponseEntity.ok("{\"message\": \"Login successful\", \"redirect\": \"/home\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }*/

    /*@RequestMapping(value = "/login", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Object> login(@RequestBody @NotNull User user) {
        *//*User u = userService.findByEmail(user.getEmail());

        if (u.getPassword().equals(user.getPassword())) {

        }*//*
        String email = user.getEmail();
        String password = user.getPassword();

        if (userService.isValidUser(email, password)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("redirect", "/home");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }*/


}
