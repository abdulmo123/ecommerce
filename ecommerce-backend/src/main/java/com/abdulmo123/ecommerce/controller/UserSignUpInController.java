package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UserSignUpInController {

    private static final Logger log = Logger.getLogger(UserSignUpInController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        user.setPassword(null);
        model.addAttribute("user", user);
        return "/home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User ();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid User user, Errors errors, Model model) {
        log.log(Level.INFO, "User : " + user.toString());
        if (errors.hasErrors()) {
            model.addAttribute("User", user);
            return "signup";
        }
        if (userService.isExisting(user.getEmail())) {
            model.addAttribute("error", "An account with this email already exists");
            return "signup";
        }

        userService.save(user);
        return "signup_success";
    }
}
