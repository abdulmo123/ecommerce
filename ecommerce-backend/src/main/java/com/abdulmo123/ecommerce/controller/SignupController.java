package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.service.EmailService;
import com.abdulmo123.ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
public class SignupController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/signup")
    public ModelAndView showSignupPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");

        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView processSignupForm (ModelAndView modelAndView, @Valid User user,
           BindingResult bindingResult, HttpServletRequest request) {

        User userExists = userService.findByEmail(user.getEmail());

        System.out.println(userExists);

        if (userExists != null) {
            modelAndView.addObject("alreadySignedUpMessage",
                    "Oops! A user has already signed up with that email!");

            modelAndView.setViewName("signup");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");

        }
        else {
            user.setEnabled(false);
            user.setConfirmationToken(UUID.randomUUID().toString());

            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";

            String message = "To set your password, please click on the link below:\n"
                    + appUrl +"/confirm?token=" + user.getConfirmationToken();

            emailService.sendEmail(user.getEmail(), "Please set a password", message);

            modelAndView.addObject("confirmationMessage",
                    "An email to set password has been sent to " + user.getEmail());

            modelAndView.setViewName("signup");
        }

        return modelAndView;
    }

    @GetMapping("/confirm")
    public ModelAndView confirmSignup (ModelAndView modelAndView, @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);

        if (user == null) {
            modelAndView.addObject("invalidToken", "Invalid confirmation link.");
        }
        else {
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");

        return modelAndView;
    }

    @PostMapping("/confirm")
    public ModelAndView confirmSignup (ModelAndView modelAndView, BindingResult bindingResult,
           @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        User user = userService.findByConfirmationToken(requestParams.get("token"));

        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
        user.setEnabled(true);

        userService.saveUser(user);

        modelAndView.setViewName("confirm");
        modelAndView.addObject("successMessage", "Password set successfully!");

        return modelAndView;
    }
}
