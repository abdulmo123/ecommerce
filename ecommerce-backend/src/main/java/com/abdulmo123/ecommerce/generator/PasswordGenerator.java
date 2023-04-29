package com.abdulmo123.ecommerce.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main (String [] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String userPassword = "";
        String encodedUserPassword = encoder.encode(userPassword);

        System.out.println("User password: " + encodedUserPassword);
    }
}
