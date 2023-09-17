package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.UserNotFoundException;
import com.abdulmo123.ecommerce.generator.PasswordGenerator;
import com.abdulmo123.ecommerce.model.CurrentUserDetails;
import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /*public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }*/

    public User saveUser(User user) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String pn = user.getPhoneNumber();
        user.setPhoneNumber(pn.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"));
        user.setPassword(passwordGenerator.encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return new CurrentUserDetails(user);
    }

    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByEmail(username);
        return user != null && user.getPassword().equals(password);
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));
    }
}
