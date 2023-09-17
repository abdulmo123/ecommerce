package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.CurrentUserDetails;
import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping(value = "/login")
    public ResponseEntity<String> getLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User currentUser = ((CurrentUserDetails) authentication.getPrincipal()).getUser();
            return ResponseEntity.ok("User authenticated successfully. user: ==> " + currentUser);
        }
        else {
            return ResponseEntity.badRequest().body("Authentication failed!");
        }
    }
}
