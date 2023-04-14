package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Lazy
    private PasswordEncoder passwordEncoder;

    public boolean isExisting (String email) {
        return (userRepository.countUserByEmail(email) != 0);
    }

    public void save (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findUserByEmail( String email) {
        return userRepository.findUserByEmail(email);
    }
}
