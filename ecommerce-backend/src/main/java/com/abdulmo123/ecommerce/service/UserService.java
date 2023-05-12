package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.model.CurrentUserDetails;
import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User can not be found!");
        }

        return new CurrentUserDetails(user);
    }

    public boolean isValidUser(String username, String password) {
        logger.info("Checking validity of user {} with password {}", username, password);
        User user = findByEmail(username);
        if (user == null) {
            logger.error("User with email {} not found.", username);
            return false;
        }
        if (!user.getPassword().equals(password)) {
            logger.error("Incorrect password for user with email {}.", username);
            return false;
        }
        logger.info("User with email {} has been successfully authenticated.", username);
        return true;
    }
}
