package com.abdulmo123.ecommerce.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedUserPassword = encoder.encode(password);
        return encodedUserPassword;
    }
}
